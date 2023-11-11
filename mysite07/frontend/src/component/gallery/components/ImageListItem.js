import React, {useRef, useState} from 'react';
import Modal from "react-modal";
import styles from '../../../assets/scss/component/gallery/ImageListItem.scss';
import modalStyles from '../../../assets/scss/component/modal/modal.scss';

Modal.setAppElement('body');

export default function ImageListItem({no, url, comment, deleteImage}) {
    const [modalIsOpen, setModalIsOpen] = useState(false);
    // console.log(no, url, comment);

    return (
        <>
            <li className={styles.ImageListItem}>
                <span 
                    style={{backgroundImage: `url(${url})`}}
                    onClick={() => setModalIsOpen(true)}
                />
                <a onClick={() => deleteImage(no)}>삭제</a>
            </li>
            <Modal
                isOpen={modalIsOpen}
                onRequestClose={() => setModalIsOpen(false)}
                shouldCloseOnOverlayClick={true}
                className={modalStyles.Modal}
                overlayClassName={modalStyles.Overlay}
                style={{content: {width: 600, height: 630}}}>
                <h1>{comment}</h1>
                <div
                    className={modalStyles['deleteImg']}
                    onClick={() => setModalIsOpen(false)} 
                />
                <div 
                    style={{backgroundImage: `url(${url})`}}
                    className={modalStyles['imgArea']}
                />
            </Modal>
        </>
    )
}