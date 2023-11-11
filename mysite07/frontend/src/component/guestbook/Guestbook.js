import React, {useEffect, useState, useRef} from 'react';
import {MySiteLayout} from "../../layout";
import styles from '../../assets/scss/component/guestbook/Guestbook.scss';
import WriteForm from './components/WriteForm';
import MessageList from './components/MessageList';

function Guestbook() {
    let isFetching = false;
    const [messages, _setMessages] = useState([]);
    const messagesRef = useRef(messages);
    // messagesRef.current를 통해 항상 최신의 messages 배열에 접근가능

    const fetchMessage = async function () {
        if (isFetching) {
            return;
        }
        isFetching = true;

        const messagesInState = this ? messagesRef.current : messages;
        const startNo = messagesInState.length === 0 ? 0 : messagesInState[messagesInState.length - 1].no;

        try {
            const response = await fetch(`/api/guestbook?no=${startNo}`, {
                method: 'get',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                }
            });

            if (!response.ok) {
                throw new Error(`${response.status} ${response.statusText}`);
            }

            const json = await response.json();
            if (json.result !== 'success') {
                throw json.message;
            }
            // console.log("data", json.data);

            json.data.length > 0 && setMessages([...messagesInState, ...json.data]);
            isFetching = false;
        } catch (err) {
            console.error(err);
        }
    }

    useEffect(() => {
        const handleWindowScroll = function () { // 무한 스크롤
            const documentHeight = window.document.body.offsetHeight;
            const viewportHeight = document.documentElement.clientHeight || window.innerHeight;
            const scrollTop = document.documentElement.scrollTop;
            if (viewportHeight + scrollTop + 10 > documentHeight) {
                fetchMessage.call(this);
            }
        }

        window.addEventListener('scroll', handleWindowScroll);
        fetchMessage.call(this);

        return () => {
            window.removeEventListener('scroll', handleWindowScroll);
        }

    }, []);

    const setMessages = function(messages) {
        messagesRef.current = messages;
        _setMessages(messages);
    }

    const notifyMessage = {
        delete: function (no) {
            setMessages(messages.filter((message) => message.no != no));
        },
        add: async function (message) {
            // console.log("message : " + JSON.stringify(message));

            const response = await fetch('/api/guestbook', {
                method: 'post',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(message)
            });

            if (!response.ok) {
                throw new Error(`${response.status} ${response.statusText}`);
            }

            const json = await response.json();
            if (json.result !== 'success') {
                throw json.message;
            }
            //  console.log(json.data);

            setMessages([json.data, ...messages]);
        }
    }

    return (
        <MySiteLayout>
            <div className={styles.Guestbook}>
                <h2>방명록</h2>
                <WriteForm notifyMessage={notifyMessage}/>
                <MessageList 
                    messages={messages} 
                    notifyMessage={notifyMessage}
                />
            </div>
        </MySiteLayout>
    );
}

export default Guestbook;