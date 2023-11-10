import React from 'react';
import ImageListItem from './ImageListItem';
import styles from '../../../assets/scss/component/gallery/ImageList.scss';

export default function ImageList({imageList}) {
    return (
        <ul className={styles.ImageList}>
            {imageList.map(item => 
                <ImageListItem
                    key={item.no}
                    no={item.no}
                    url={item.imageUrl}
                    comment={item.comment}
                />)}
        </ul>
    )
}