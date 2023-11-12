import React from 'react';
import {MySiteLayout} from "../../layout";
import styles from '../../assets/scss/component/user/User.scss';

export default function SignIn() {
    return (
        <MySiteLayout>
            {/* <div className={styles.User}>
                <h2>Sign In</h2>
            </div> */}

            <div className={styles.User} id="user">
				<form 
                    id={styles['login-form']} 
                    name="loginform" 
                    onSubmit={ loginSubmit }>
                    {/* method="post" action="/user/auth"> */}
					<label className={'block-label'} for={'email'}>이메일</label>
                    <input id={'email'} name={'email'} type={'text'} value={'${email }'}></input>
					
                    {/* 
					<label class="block-label" >패스워드</label>
					<input name="password" type="password" value="">
					<c:if test="${not empty email }">
						<p>
							로그인이 실패 했습니다.
						</p>
					</c:if>
					<input type="submit" value="로그인"> */}
				</form>
			</div>
        </MySiteLayout>
    );
}

{/* <form 
    ref={refForm} 
    className={styles.FormUpload} 
    onSubmit={ addImageSubmit }>
    <input type={'text'} name={'comment'} placeholder={'설명(코멘트)'}/>
    <br/><br/>
    <label>이미지(사진)</label>
    <br/>
    <input type={'file'} name={'uploadImage'} placeholder={'이미지(사진)'}/>
</form> */}