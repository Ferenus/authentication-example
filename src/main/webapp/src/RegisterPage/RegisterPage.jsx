import React from 'react';
import {Link} from 'react-router-dom';
import {connect} from 'react-redux';

import {userActions} from '../_actions';

class RegisterPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            user: {
                email: '',
                password: ''
            },
            submitted: false,
            emailErrors: [],
            passErrors: [],
        };
    }

    handleChange = (event) => {
        const {name, value} = event.target;
        const {user, submitted} = this.state;
        this.setState({
            user: {
                ...user,
                [name]: value
            }
        });
        if (submitted) {
            if (name === "email") {
                this.validateEmail(value);
            } else if (name === "password") {
                this.validatePassword(value);
            }
        }
    };

    handleSubmit = (event) => {
        event.preventDefault();
        const {user} = this.state;
        const emailErrors = this.validateEmail(user.email);
        const passErrors = this.validatePassword(user.password);

        const {dispatch} = this.props;
        this.setState({
            submitted: true,
        });
        if (emailErrors.length === 0 && passErrors.length === 0) {
            dispatch(userActions.register(user));
        }
    };

    validateEmail(email) {
        const emailErrors = [];
        if (email) {
            if (!/\S+@\S+\.\S+/.test(email)) {
                emailErrors.push(`"${email}" is not a valid Email address.`);
            }
        } else {
            emailErrors.push("Email is required.");
        }
        this.setState({
            emailErrors: emailErrors,
        });
        return emailErrors;
    };

    validatePassword(password) {
        const passErrors = [];
        if (password) {
            if (password.length < 8) {
                passErrors.push("Your password must be at least 8 characters");
            }
            if (password.search(/[a-z]/i) < 0) {
                passErrors.push("Your password must contain at least one letter.");
            }
            if (password.search(/[0-9]/) < 0) {
                passErrors.push("Your password must contain at least one digit.");
            }
        } else {
            passErrors.push("Password is required.");
        }
        this.setState({
            passErrors: passErrors,
        });
        return passErrors;
    };

    render() {
        const {registering} = this.props;
        const {user, emailErrors, passErrors} = this.state;
        return (
            <div className="col-md-6 col-md-offset-3">
                <h2>Register</h2>
                <form name="form" onSubmit={this.handleSubmit}>
                    <div className={'form-group' + (emailErrors.length !== 0 ? ' has-error' : '')}>
                        <label htmlFor="email">Email</label>
                        <input type="text" className="form-control" name="email" value={user.email}
                               onChange={this.handleChange}/>
                        {emailErrors.map((err, index) => <div className="help-block" key={index}>{err}</div>)}
                    </div>
                    <div className={'form-group' + (passErrors.length !== 0 ? ' has-error' : '')}>
                        <label htmlFor="password">Password</label>
                        <input type="password" className="form-control" name="password" value={user.password}
                               onChange={this.handleChange}/>
                        {passErrors.map((err, index) => <div className="help-block" key={index}>{err}</div>)}
                    </div>
                    <div className="form-group">
                        <button className="btn btn-primary">Register</button>
                        {registering &&
                        <img alt=""
                             src="data:image/gif;base64,R0lGODlhEAAQAPIAAP///wAAAMLCwkJCQgAAAGJiYoKCgpKSkiH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAEAAQAAADMwi63P4wyklrE2MIOggZnAdOmGYJRbExwroUmcG2LmDEwnHQLVsYOd2mBzkYDAdKa+dIAAAh+QQJCgAAACwAAAAAEAAQAAADNAi63P5OjCEgG4QMu7DmikRxQlFUYDEZIGBMRVsaqHwctXXf7WEYB4Ag1xjihkMZsiUkKhIAIfkECQoAAAAsAAAAABAAEAAAAzYIujIjK8pByJDMlFYvBoVjHA70GU7xSUJhmKtwHPAKzLO9HMaoKwJZ7Rf8AYPDDzKpZBqfvwQAIfkECQoAAAAsAAAAABAAEAAAAzMIumIlK8oyhpHsnFZfhYumCYUhDAQxRIdhHBGqRoKw0R8DYlJd8z0fMDgsGo/IpHI5TAAAIfkECQoAAAAsAAAAABAAEAAAAzIIunInK0rnZBTwGPNMgQwmdsNgXGJUlIWEuR5oWUIpz8pAEAMe6TwfwyYsGo/IpFKSAAAh+QQJCgAAACwAAAAAEAAQAAADMwi6IMKQORfjdOe82p4wGccc4CEuQradylesojEMBgsUc2G7sDX3lQGBMLAJibufbSlKAAAh+QQJCgAAACwAAAAAEAAQAAADMgi63P7wCRHZnFVdmgHu2nFwlWCI3WGc3TSWhUFGxTAUkGCbtgENBMJAEJsxgMLWzpEAACH5BAkKAAAALAAAAAAQABAAAAMyCLrc/jDKSatlQtScKdceCAjDII7HcQ4EMTCpyrCuUBjCYRgHVtqlAiB1YhiCnlsRkAAAOwAAAAAAAAAAAA=="/>
                        }
                        <Link to="/login" className="btn btn-link">Cancel</Link>
                    </div>
                </form>
            </div>
        );
    }
}

function mapStateToProps(state) {
    const {registering} = state.registration;
    return {
        registering
    };
}

const connectedRegisterPage = connect(mapStateToProps)(RegisterPage);
export {connectedRegisterPage as RegisterPage};