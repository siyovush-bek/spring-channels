import React from 'react';
import { Link } from 'react-router-dom'
import '../assets/login.css'

export default function Signup() {

    const [inputs, setInputs] = React.useState({});

    const handleChange = (event) => {
        const name = event.target.name;
        const value = event.target.value;
        setInputs(values => ({...values, [name]: value}))
      }
    
      const handleSubmit = (event) => {
        event.preventDefault();
        
        const userData = {
            "username": inputs.username,
            "password": inputs.password,
        }

        fetch('http://localhost:8881/api/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(userData),
        })
        .then((response) => response.json())
        .then((data) => {
            console.log('Success:', data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });

      }



    return (
        <form className="form-signin" onSubmit={handleSubmit}>
            <h1 className="h3 mb-3 font-weight-normal">Enter Your credentials</h1>
            
            <label htmlFor="inputEmail" className="sr-only">Username</label>
            <input type="text" id="inputEmail" name="username" className="form-control" value={inputs.username || ""} onChange={handleChange} placeholder="Username" required autoFocus />
            
            <label htmlFor="inputPassword" className="sr-only">Password</label>
            <input type="password" id="inputPassword" name="password" className="form-control" value={inputs.password || ""} onChange={handleChange} placeholder="Password" required />
            
            <label htmlFor="inputConfirmPassword" className="sr-only">Confirm Your Password</label>
            <input type="password" id="inputConfirmPassword" name="confirmPassword" className="form-control" value={inputs.confirmPassword || ""} onChange={handleChange} placeholder="Confirm Your Password" required />
            
            <p>Already have an account? <Link to="/login">Log In!</Link></p>
            <button className="btn btn-lg btn-primary btn-block" type="submit">Sign Up</button>
        </form>
    )
}