import React from 'react';
import { useState } from 'react'

export default function LoginForm() {
    const [isLogin,setIsLogin] = useState(true);

    const[signUpEmail,setSignUpEmail] = useState('');
    const[signUpPassword,setSignUpPassword] = useState('');
    const[signUpConfirmPassword,setSignUpConfirmPassword] = useState('');


    return(
        <div className='container'>
            <div className='form-container'>
                <div className='form-toggle'>
                    <button className={isLogin ? 'active' : ""} onClick={() => setIsLogin(true)}>Login</button>
                    <button className={!isLogin ? 'active' : ""} onClick={() => setIsLogin(false)}>SignUp</button>
                    </div>
                    {isLogin ? <>
                        <div className='form'>
                            <h2>User Login</h2>
                            <input type='email' placeholder='Enter your email'/>
                             <input type='password' placeholder='Enter your Password'/>
                             <a href='#'>Forgot Password?</a>
                             <button>Login</button>
                             <p>Not a User? <a href='#' onClick={() => setIsLogin(false)}>Register Here</a></p>
                            </div>
                        </> : <>
                        <div className='form'>
                            <h2>Signup Form</h2>
                             <input type='email'
                                    placeholder='Enter your email'
                                    value={signupEmail}
                                    onChange={(e) => setSignupEmail(e.target.value)}
                             />
                             <input type='password'
                                    placeholder='Enter your Password'
                                    value={signUpPassword}
                                    onChange={(e) => setSignUpPassword(e.target.value)}
                             />
                             <input type='password'
                                    placeholder='Confirm your Password'
                                    value={signUpConfirmPassword}
                                    onChange={(e) => setSignUpConfirmPassword(e.target.value)}
                             />
                             <button>Signup</button>
                        </div>
                        </>}
                </div>
         </div>
        )
    }