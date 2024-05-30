import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';
import React from 'react';
import {Route} from 'react-router-dom';

import Password from './password/password';
import Sessions from './sessions/sessions';
import Settings from './settings/settings';

const AccountRoutes = () => (
    <div>
        <ErrorBoundaryRoutes>
            <Route element={<Settings />} path="settings" />

            <Route element={<Password />} path="password" />

            <Route element={<Sessions />} path="sessions" />
        </ErrorBoundaryRoutes>
    </div>
);

export default AccountRoutes;
