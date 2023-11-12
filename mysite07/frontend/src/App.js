import React from 'react';
import {Routes, Route} from 'react-router';
import './assets/scss/App.scss';

import {Main} from './component/main';
import {Guestbook} from './component/guestbook';
import {Gallery} from './component/gallery';
import SignIn from './component/user/SignIn';
import SignUp from './component/user/SignUp';
import Settings from './component/user/Settings';
import Admin from './component/admin/Admin';

export default function App() {
    //*
    return (
        <Routes>
            <Route path='/gallery' element={<Gallery />}/>
            <Route path='/guestbook' element={<Guestbook />}/>
            <Route path='/user/login' element={<SignIn />}/>
            <Route path='/user/join' element={<SignUp />}/>
            <Route path='/user/settings' element={<Settings />}/>
            <Route path='/admin' element={<Admin />}/>
            <Route path='*' element={<Main />}/>
        </Routes>
    );s
    /*/
    return useRoutes([
        { path:'/gallery', element: <Gallery /> },
        { path:'/guestbook', element: <Guestbook /> },
        { path:'/user/login', element: <SignIn /> },
        { path:'/user/join', element: <SignUp /> },
        { path:'/user/settings', element: <Settings /> },
        { path:'*', element: <Main /> }
    ]);
    */
}