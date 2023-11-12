import React, {Fragment} from 'react';
import Header from "./Header";
import Navigation from "./Navigation";
import styles from '../../assets/scss/layout/admin/Layout.scss';

export default function AdminLayout({children}) {
    return (
        <Fragment>
            <Header/>
            {/* <div className={styles.Content}>
                {children}
            </div>
             */}
            <Navigation/>
        </Fragment>
    );
}