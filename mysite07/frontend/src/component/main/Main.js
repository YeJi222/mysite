import React, {useState, useEffect} from 'react';
import {MySiteLayout} from "../../layout";
import styles from '../../assets/scss/component/main/Main.scss';

export default function Main() {
    const [mainInfo, setMainInfo] = useState(null);

    const fetchMainInfo = async () => {
        try {
            const response = await fetch('/api/main', {
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
                throw new Error(`${json.result} ${json.message}`);
            }

            console.log(json.data);

            setMainInfo(json.data);
        } catch (err) {
            console.error(err);
        }
    };

    useEffect(() => {
        fetchMainInfo();
    }, []);

    return (
        <MySiteLayout>
            <div className={styles.Main}>
                <div id={styles['site-introduction']}>
                    {
                        mainInfo === null ?
                        null :
                        <>
                            <img id={styles['profile']} src={mainInfo.profile}/>
                            <h2 style={{whiteSpace: 'pre-line'}}> {/* 줄바꿈 적용 */}
                                {mainInfo.welcome}
                            </h2>
                            <p style={{whiteSpace: 'pre-line'}}>
                                {mainInfo.description}
                                <br></br><br></br>
                                <a href='/guestbook'>방명록</a>에 글 남기기<br></br>
                            </p>
                        </>
                    }
                </div>
            </div>
        </MySiteLayout>
    );
}