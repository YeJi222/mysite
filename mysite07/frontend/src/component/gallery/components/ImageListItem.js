import React from 'react';
import styles from '../../../assets/scss/component/gallery/ImageListItem.scss';

export default function ImageListItem({no, url, comment}) {
    console.log(no, url, comment);

    // url = 'file:///Users/yeji/mysite-uploads/gallery/202310116214522.jpg';
    // url = 'https://ifh.cc/g/GSqKq0.jpg';

    return (
        <li className={styles.ImageListItem}>
            <span style={{backgroundImage: `url(${url})`}}/>
            <a onClick={() => {}}>삭제</a>
        </li>
    )
}