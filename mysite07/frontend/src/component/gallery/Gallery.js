import React, {useState, useEffect} from 'react';
import {MySiteLayout} from "../../layout";
import Header from "./components/Header";
import ImageList from "./components/ImageList";
import styles from '../../assets/scss/component/gallery/Gallery.scss';

export default function Index() {
    const [imageList, setImageList] = useState([]);

    const fetchImages = async () => {
        try {
            const response = await fetch('/api/gallery', {
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

            setImageList(json.data);
        } catch (err) {
            console.error(err);
        }
    };

    useEffect(() => {
        fetchImages();
    }, []);
    

    const addImage = async (comment, file) => {
        try {
            // Create FormData
            const formData = new FormData();
            formData.append('comments', comment);
            formData.append('file', file);

            // console.log(comment, file);

            // Post
            const response = await fetch(`/api/gallery`, {
                method: 'post',
                headers: {'Accept': 'application/json'},
                body: formData
            });

            if (!response.ok) {
                throw `${response.status} ${response.statusText}`;
            }

            // API success?
            const json = await response.json();
            if (json.result !== 'success') {
                throw json.message;
            }

            // Rendering(Update)
            setImageList([json.data, ...imageList]);
        } catch (err) {
            console.error(err);
        }
    };

    return (
        <MySiteLayout>
            <div className={styles.Gallery}>
                <Header addImage={addImage}/>
                {
                    imageList === null ?
                    null :
                    <ImageList imageList={imageList} />
                }
            </div>
        </MySiteLayout>
    )
}