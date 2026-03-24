import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export default function LoginForm() {
    const navigate = useNavigate();

    const [isLogin, setIsLogin] = useState(true);
    const [loginEmail, setLoginEmail] = useState("");
    const [loginPassword, setLoginPassword] = useState("");

    const [signupName, setSignupName] = useState('');
    const [signupEmail, setSignupEmail] = useState('');
    const [signupPassword, setSignupPassword] = useState('');
    const [signupConfirmPassword, setSignupConfirmPassword] = useState('');

    const handleSignup = async () => {
        if (signupPassword !== signupConfirmPassword) {
            toast.error("Passwords do not match!");
            return;
        }
        const body = { customerName: signupName, email: signupEmail, password: signupPassword };
        try {
            const response = await fetch("http://localhost:8080/api/customers/register", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(body)
            });
            if (response.ok) {
                toast.success("Signup Successful!");
                setIsLogin(true);
            } else {
                toast.error("Signup failed!");
            }
        } catch {
            toast.error("Something went wrong!");
        }
    };

    const handleLogin = async () => {
        const body = { email: loginEmail, password: loginPassword };
        try {
            const response = await fetch("http://localhost:8080/api/customers/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(body)
            });

            if (response.ok) {
                const data = await response.json();
                localStorage.setItem("token", data.token);
                localStorage.setItem("customerName", data.customerName);
                localStorage.setItem("email", data.email);

                toast.success("Login Successful!");
                setTimeout(() => {
                    navigate("/dashboard");
                }, 1000);
            } else {
                toast.error("Wrong Credentials!");
            }
        } catch {
            toast.error("Server Error!");
        }
    };

    return (
        <div className='container'>
            <ToastContainer position="top-right" autoClose={2000} hideProgressBar />
            <div className='form-container'>
                <div className='form-toggle'>
                    <button className={isLogin ? 'active' : ''} onClick={() => setIsLogin(true)}>Login</button>
                    <button className={!isLogin ? 'active' : ''} onClick={() => setIsLogin(false)}>SignUp</button>
                </div>

                {isLogin ? (
                    <div className='form'>
                        <h2>User Login</h2>
                        <input type='email' placeholder='Enter your email' value={loginEmail} onChange={e => setLoginEmail(e.target.value)} />
                        <input type='password' placeholder='Enter your Password' value={loginPassword} onChange={e => setLoginPassword(e.target.value)} />
                        <a href='#'>Forgot Password?</a>
                        <button onClick={handleLogin}>Login</button>
                        <p>Not a User? <a href='#' onClick={() => setIsLogin(false)}>Register Here</a></p>
                    </div>
                ) : (
                    <div className='form'>
                        <h2>Signup Form</h2>
                        <input type='text' placeholder='Enter your Name' value={signupName} onChange={e => setSignupName(e.target.value)} />
                        <input type='email' placeholder='Enter your Email' value={signupEmail} onChange={e => setSignupEmail(e.target.value)} />
                        <input type='password' placeholder='Enter your Password' value={signupPassword} onChange={e => setSignupPassword(e.target.value)} />
                        <input type='password' placeholder='Confirm your Password' value={signupConfirmPassword} onChange={e => setSignupConfirmPassword(e.target.value)} />
                        <button onClick={handleSignup}>Signup</button>
                    </div>
                )}
            </div>
        </div>
    );
}