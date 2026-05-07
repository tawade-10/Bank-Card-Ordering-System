import React, { useState, useRef, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { FaEye, FaEyeSlash } from "react-icons/fa";
import { MdOutlineEmail } from "react-icons/md";
import { FaLock } from "react-icons/fa6";
import { IoMdPerson } from "react-icons/io";

export default function LoginForm() {
    const navigate = useNavigate();
    const [isLogin, setIsLogin] = useState(true);

    const loginRef = useRef(null);
    const signUpRef = useRef(null);

    useEffect(() => {
        if (isLogin && loginRef.current) {
            loginRef.current.focus();
        }
        if (!isLogin && signUpRef.current) {
            signUpRef.current.focus();
        }
    }, [isLogin]);

    const [formData, setFormData] = useState({
        loginEmail: '',
        loginPassword: '',
        signupName: '',
        signupEmail: '',
        signupPassword: '',
        signupConfirmPassword: ''
    });

    const [errors, setErrors] = useState({});

    const handleLoginReset = () => {
        setFormData(prev => ({
            ...prev,
            loginEmail: "",
            loginPassword: ""
        }));
        setErrors({});
    };

    const handleSignupReset = () => {
        setFormData(prev => ({
            ...prev,
            signupName: "",
            signupEmail: "",
            signupPassword: "",
            signupConfirmPassword: ""
        }));
        setErrors({});
    };

    const validateLogin = () => {
        const loginErrors = {};
        if (!formData.loginEmail) loginErrors.loginEmail = "Email is required!";
        else if (!/\S+@\S+\.\S+/.test(formData.loginEmail)) loginErrors.loginEmail = "Invalid Email format!";
        if (!formData.loginPassword) loginErrors.loginPassword = "Password is required!";
        setErrors(loginErrors);
        return Object.keys(loginErrors).length === 0;
    };

    const validateSignup = () => {
        const signupErrors = {};
        if (!formData.signupName) signupErrors.signupName = "Name is required!";
        if (!formData.signupEmail) signupErrors.signupEmail = "Email is required!";
        else if (!/\S+@\S+\.\S+/.test(formData.signupEmail)) signupErrors.signupEmail = "Invalid Email format!";
        if (!formData.signupPassword) signupErrors.signupPassword = "Password is required!";
        else if (formData.signupPassword.length < 6) signupErrors.signupPassword = "Password must be at least 6 characters!";
        if (!formData.signupConfirmPassword) signupErrors.signupConfirmPassword = "Confirm your password!";
        else if (formData.signupPassword !== formData.signupConfirmPassword) signupErrors.signupConfirmPassword = "Passwords do not match!";
        setErrors(signupErrors);
        return Object.keys(signupErrors).length === 0;
    };

    const handleLogin = async () => {
        if (!validateLogin()) return;

        const body = { email: formData.loginEmail, password: formData.loginPassword };

        try {
            const response = await fetch("http://localhost:8080/api/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(body)
            });

            if (response.ok) {
                const data = await response.json();
                localStorage.setItem("token", data.token);
                localStorage.setItem("customerName", data.customerName);
                localStorage.setItem("email", data.email);
                localStorage.setItem("role", data.roles);
                localStorage.setItem("userId", data.userId);
                toast.success("Login Successful!");
                if (data.roles === "ADMIN") {
                    navigate("/admin/dashboard");
                } else if (data.roles === "CUSTOMER") {
                    navigate("/dashboard");
                } else {
                    toast.error("Unknown Role Assigned!");
                }
            } else {
                toast.error("Wrong Credentials!");
            }
        } catch {
            toast.error("Server Error!");
        }
    };

    const handleSignup = async () => {
        if (!validateSignup()) return;

        const body = {
            customerName: formData.signupName,
            email: formData.signupEmail,
            password: formData.signupPassword,
            roles: "CUSTOMER"
        };

        try {
            const response = await fetch("http://localhost:8080/api/register", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(body)
            });
            if (response.ok) {
                toast.success("Signup Successful!");
                handleSignupReset();
                setIsLogin(true);
            } else {
                toast.error("Signup failed!");
            }
        } catch {
            toast.error("Something went wrong!");
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const [showPassword, setShowPassword] = useState(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState(false);

    return (
        <div className='container'>
            <ToastContainer position="top-right" autoClose={2000} hideProgressBar />

            <div className='form-container'>
                <div className='form-toggle'>
                    <button className={isLogin ? 'active' : ''} onClick={() => setIsLogin(true)}>Login</button>
                    <button className={!isLogin ? 'active' : ''} onClick={() => setIsLogin(false)}>SignUp</button>
                </div>

                {/* LOGIN FORM */}
                {isLogin ? (
                    <div className='form'>
                        <h2>User Login</h2>

                        <div className="email-field">
                            <span className="email-icon"><MdOutlineEmail /></span>
                            <input
                                type='email'
                                name='loginEmail'
                                placeholder='Enter your email'
                                value={formData.loginEmail}
                                onChange={handleChange}
                                ref={loginRef}
                            />
                        </div>
                        {errors.loginEmail && <span className='error'>{errors.loginEmail}</span>}

                        <div className="password-field">
                            <span className="lock-icon"><FaLock /></span>
                            <input
                                type={showPassword ? 'text' : 'password'}
                                name='loginPassword'
                                placeholder='Enter your Password'
                                value={formData.loginPassword}
                                onChange={handleChange}
                            />
                            <span className="password-icon" onClick={() => setShowPassword(prev => !prev)}>
                                {showPassword ? <FaEyeSlash /> : <FaEye />}
                            </span>
                        </div>
                        {errors.loginPassword && <span className='error'>{errors.loginPassword}</span>}

                        <div className="reset-links">
                            <a href="#">Forgot Password?</a>
                            <span onClick={handleLoginReset} className="reset-btn">Reset Fields</span>
                        </div>

                        <button className="primary-btn" onClick={handleLogin}>Login</button>
                        <p>Not a User?{" "}<a href="#" onClick={() => {setIsLogin(false);
                                                                       handleLoginReset();

                            }}
                          >
                            Register Here
                          </a>
                        </p>
                    </div>
                ) : (

                    /* SIGNUP FORM */
                    <div className='form'>
                        <h2>Signup Form</h2>

                        <div className="name-field">
                            <span className="name-icon"><IoMdPerson /></span>
                            <input
                                type='text'
                                name='signupName'
                                placeholder='Enter your Name'
                                value={formData.signupName}
                                onChange={handleChange}
                                ref={signUpRef}
                            />
                        </div>
                        {errors.signupName && <span className='error'>{errors.signupName}</span>}

                        <div className="email-field">
                            <span className="email-icon"><MdOutlineEmail /></span>
                            <input
                                type='email'
                                name='signupEmail'
                                placeholder='Enter your Email'
                                value={formData.signupEmail}
                                onChange={handleChange}
                            />
                        </div>
                        {errors.signupEmail && <span className='error'>{errors.signupEmail}</span>}

                        <div className="password-field">
                            <span className="lock-icon"><FaLock /></span>
                            <input
                                type={showPassword ? 'text' : 'password'}
                                name='signupPassword'
                                placeholder='Enter your Password'
                                value={formData.signupPassword}
                                onChange={handleChange}
                            />
                            <span className="password-icon" onClick={() => setShowPassword(prev => !prev)}>
                                {showPassword ? <FaEyeSlash /> : <FaEye />}
                            </span>
                        </div>
                        {errors.signupPassword && <span className='error'>{errors.signupPassword}</span>}

                        <div className="password-field">
                            <span className="lock-icon"><FaLock /></span>
                            <input
                                type={showConfirmPassword ? 'text' : 'password'}
                                name='signupConfirmPassword'
                                placeholder='Confirm your Password'
                                value={formData.signupConfirmPassword}
                                onChange={handleChange}
                            />
                            <span className="password-icon" onClick={() => setShowConfirmPassword(prev => !prev)}>
                                {showConfirmPassword ? <FaEyeSlash /> : <FaEye />}
                            </span>
                        </div>
                        {errors.signupConfirmPassword && <span className='error'>{errors.signupConfirmPassword}</span>}

                        <div className="reset-links">
                            <span onClick={handleSignupReset} className="reset-btn">Reset Fields</span>
                        </div>

                        <div className="signup-button">
                            <button onClick={handleSignup}>Signup</button>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
}