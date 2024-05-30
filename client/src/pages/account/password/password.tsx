import {useAppDispatch, useAppSelector} from 'app/config/store';
import PasswordStrengthBar from 'app/shared/layout/password/password-strength-bar';
import {getSession} from 'app/shared/reducers/authentication';
import React, {useEffect, useState} from 'react';
import {Translate, ValidatedField, ValidatedForm, translate} from 'react-jhipster';
import {toast} from 'react-toastify';
import {Button, Col, Row} from 'reactstrap';

import {reset, savePassword} from './password.reducer';

export const PasswordPage = () => {
    const [password, setPassword] = useState('');
    const dispatch = useAppDispatch();

    useEffect(() => {
        dispatch(reset());
        dispatch(getSession());
        return () => {
            dispatch(reset());
        };
    }, []);

    const handleValidSubmit = ({currentPassword, newPassword}) => {
        dispatch(savePassword({currentPassword, newPassword}));
    };

    const updatePassword = (event) => setPassword(event.target.value);

    const account = useAppSelector((state) => state.authentication.account);
    const successMessage = useAppSelector((state) => state.password.successMessage);
    const errorMessage = useAppSelector((state) => state.password.errorMessage);

    useEffect(() => {
        if (successMessage) {
            toast.success(translate(successMessage));
        } else if (errorMessage) {
            toast.error(translate(errorMessage));
        }
        dispatch(reset());
    }, [successMessage, errorMessage]);

    return (
        <div>
            <Row className="justify-content-center">
                <Col md="8">
                    <h2 id="password-title">
                        <Translate contentKey="password.title" interpolate={{username: account.login}}>
                            Password for {account.login}
                        </Translate>
                    </h2>

                    <ValidatedForm id="password-form" onSubmit={handleValidSubmit}>
                        <ValidatedField
                            data-cy="currentPassword"
                            label={translate('global.form.currentpassword.label')}
                            name="currentPassword"
                            placeholder={translate('global.form.currentpassword.placeholder')}
                            type="password"
                            validate={{
                                required: {
                                    value: true,
                                    message: translate('global.messages.validate.newpassword.required'),
                                },
                            }}
                        />

                        <ValidatedField
                            data-cy="newPassword"
                            label={translate('global.form.newpassword.label')}
                            name="newPassword"
                            onChange={updatePassword}
                            placeholder={translate('global.form.newpassword.placeholder')}
                            type="password"
                            validate={{
                                required: {
                                    value: true,
                                    message: translate('global.messages.validate.newpassword.required'),
                                },
                                minLength: {
                                    value: 4,
                                    message: translate('global.messages.validate.newpassword.minlength'),
                                },
                                maxLength: {
                                    value: 50,
                                    message: translate('global.messages.validate.newpassword.maxlength'),
                                },
                            }}
                        />

                        <PasswordStrengthBar password={password} />

                        <ValidatedField
                            data-cy="confirmPassword"
                            label={translate('global.form.confirmpassword.label')}
                            name="confirmPassword"
                            placeholder={translate('global.form.confirmpassword.placeholder')}
                            type="password"
                            validate={{
                                required: {
                                    value: true,
                                    message: translate('global.messages.validate.confirmpassword.required'),
                                },
                                minLength: {
                                    value: 4,
                                    message: translate('global.messages.validate.confirmpassword.minlength'),
                                },
                                maxLength: {
                                    value: 50,
                                    message: translate('global.messages.validate.confirmpassword.maxlength'),
                                },
                                validate: (v) => v === password || translate('global.messages.error.dontmatch'),
                            }}
                        />

                        <Button color="success" data-cy="submit" type="submit">
                            <Translate contentKey="password.form.button">Save</Translate>
                        </Button>
                    </ValidatedForm>
                </Col>
            </Row>
        </div>
    );
};

export default PasswordPage;
