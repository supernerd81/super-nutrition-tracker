import { Navigate, Outlet } from "react-router-dom";
import {AppUser} from "./model/AppUser.ts";

type Props = {
    appUser: AppUser | undefined | null
}

export default function ProtectedRoutes({appUser}: Readonly<Props>) {

    if(appUser === undefined) {
        return <div>loading ...</div>
    }

    return appUser ? <Outlet /> : <Navigate to="/" />
}