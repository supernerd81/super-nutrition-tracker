import UpdateProfileForm from "../forms/UpdateProfileForm.tsx";
import {AppUser} from "../model/AppUser.ts";

type Props = {
    appUser: AppUser | undefined | null
    setAppUser: React.Dispatch<React.SetStateAction<AppUser | null | undefined>>
}

export default function UpdateProfile(props: Readonly<Props>) {

    return (
        <div className={"col-12"}>
            {<UpdateProfileForm appUser={props.appUser} setAppUser={props.setAppUser} />}
        </div>
    )

}