import React from 'react';
import PropTypes from 'prop-types';
import styles from '../../../assets/scss/component/guestbook/Message.scss';

export default function Message({no, name, contents, notifyMessage}) {
    
    // console.log(no, name, contents);
    
    return (
        <li className={styles.Message}>
            <strong>{name}</strong>
            <p>
                {contents && contents.split('\n').map((line, index) => index > 0 ?
                    <span key={`${no}-${index}`}>
                        <br/>
                        {line}
                    </span> : line)}
            </p>
            <strong/>
            <a onClick={() => notifyMessage(no)}>삭제</a>
        </li>
    );
}

Message.propTypes = {
    no: PropTypes.number.isRequired,
    name: PropTypes.string.isRequired,
    contents: PropTypes.string.isRequired
}