import React, {useRef} from 'react';
import styles from '../../../assets/scss/component/guestbook/WriteForm.scss';

export default function WriteForm({notifyMessage}) {
    const refForm = useRef(null);

    const handleSubmit = function (e) {
        e.preventDefault();
        try {
            const newMessage = Array
                .from(e.target, (input) => {
                    // simple validatation of empty string
                    if (input.value === '') {
                        throw `validation ${input.placeholder} is empty ''`;
                    }
                    return {n: input.name, v: input.value}; // 객체 반환 
                })
                .filter(({n}) => n !== '') // n이 비어 있지 않은 경우에만 
                .reduce((res, {n, v}) => { // 하나의 객체로 축소 
                    res[n] = v;
                    return res;
                }, {});

            // newMessage 형태 : {name: '11', password: '11', contents: '11'}
            refForm.current.reset();
            notifyMessage.add(newMessage);
        } catch (err) {
            console.error(err);
        }
    }

    return (
        <form
            ref={refForm}
            onSubmit={handleSubmit}
            className={styles.WriteForm}>
            <input
                type={'text'}
                name={'name'}
                placeholder={'이름'}
                autoComplete={'off'}/> {/* 자동완성 기능 off */}
            <input
                type={'password'}
                name={'password'}
                placeholder={'비밀번호'}
                autoComplete={'off'}/>
            <textarea
                name={'contents'}
                placeholder={'메세지(내용)'}/>
            <input
                type={'submit'}
                value={'보내기'}
                autoComplete={'off'}/>
        </form>
    );
}