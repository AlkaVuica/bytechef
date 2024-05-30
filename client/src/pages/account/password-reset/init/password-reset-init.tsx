import {useAppDispatch, useAppSelector} from 'app/config/store';
import React, {useEffect} from 'react';
import {Translate, ValidatedField, ValidatedForm, isEmail, translate} from 'react-jhipster';
import {toast} from 'react-toastify';
import {Alert, Button, Col, Row} from 'reactstrap';

import {handlePasswordResetInit, reset} from '../password-reset.reducer';

export const PasswordResetInit = () => {
    const dispatch = useAppDispatch();

    useEffect(
        () => () => {
            dispatch(reset());
        },
        []
    );

    const handleValidSubmit = ({email}) => {
        dispatch(handlePasswordResetInit(email));
    };

    const successMessage = useAppSelector((state) => state.passwordReset.successMessage);

    useEffect(() => {
        if (successMessage) {
            toast.success(translate(successMessage));
        }
    }, [successMessage]);

    return (
        <div>
            <Row className="justify-content-center">
                <Col md="8">
                    <h1>
                        <Translate contentKey="reset.request.title">Reset your password</Translate>
                    </h1>

                    <Alert color="warning">
                        <p>
                            <Translate contentKey="reset.request.messages.info">
                                Enter the email address you used to register
                            </Translate>
                        </p>
                    </Alert>

                    <ValidatedForm onSubmit={handleValidSubmit}>
                        <ValidatedField
                            data-cy="emailResetPassword"
                            label={translate('global.form.email.label')}
                            name="email"
                            placeholder={translate('global.form.email.placeholder')}
                            type="email"
                            validate={{
                                required: {value: true, message: translate('global.messages.validate.email.required')},
                                minLength: {value: 5, message: translate('global.messages.validate.email.minlength')},
                                maxLength: {value: 254, message: translate('global.messages.validate.email.maxlength')},
                                validate: (v) => isEmail(v) || translate('global.messages.validate.email.invalid'),
                            }}
                        />

                        <Button color="primary" data-cy="submit" type="submit">
                            <Translate contentKey="reset.request.form.button">Reset password</Translate>
                        </Button>
                    </ValidatedForm>
                </Col>
            </Row>
        </div>
    );
};

export default PasswordResetInit;
