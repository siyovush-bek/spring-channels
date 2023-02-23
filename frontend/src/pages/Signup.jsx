import { Link } from 'react-router-dom'
import '../assets/login.css'

export default function Signup() {
    return (
        <form class="form-signin">
            <h1 class="h3 mb-3 font-weight-normal">Enter Your credentials</h1>
            <label for="inputEmail" class="sr-only">Username</label>
            <input type="text" id="inputEmail" class="form-control" placeholder="Username" required autofocus />
            <label for="inputPassword" class="sr-only">Password</label>
            <input type="password" id="inputPassword" class="form-control" placeholder="Password" required />
            <label for="inputPassword" class="sr-only">Confirm Your Password</label>
            <input type="password" id="inputPassword" class="form-control" placeholder="Confirm Your Password" required />
            <p>Already have an account? <Link to="/login">Log In!</Link></p>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign Up</button>
        </form>
    )
}