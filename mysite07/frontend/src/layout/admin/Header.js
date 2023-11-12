import React from 'react';
import {NavLink} from "react-router-dom";
import styles from '../../assets/scss/layout/admin/Header.scss';

export default function Header() {
    return (
        <div className={styles.header} id={'header'}>
            <h1><NavLink to={'/admin'}>관리자페이지</NavLink></h1>
            <ul>
                <li><NavLink to={'/'}>사이트 메인</NavLink></li>
                <li><NavLink to={'/user/logout'}>로그아웃</NavLink></li>
            </ul>
        </div>
    );
}

{/* <div id="header">
	<h1>관리자페이지</h1>
	<ul>
		<li><a href="${pageContext.request.contextPath }">사이트 메인</a><li>
		<li><a href="${pageContext.request.contextPath }/user/logout">로그아웃</a><li>
	</ul>
</div> */}