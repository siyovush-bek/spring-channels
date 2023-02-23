import { Link } from 'react-router-dom'
import '../assets/login.css'

export default function Login() {
    return (
        <form class="form-signin">
        <h1 class="h3 mb-3 font-weight-normal">Please log in</h1>
        <label for="inputEmail" class="sr-only">Username</label>
        <input type="text" id="inputEmail" class="form-control" placeholder="Username" required autofocus />
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="Password" required />
        <p>Not registered yet? <Link to="/signup">Sign Up!</Link></p>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Log in</button>
        </form>
    )
}