import NewMealForm from "../forms/NewMealForm.tsx";
import {AppUser} from "../model/AppUser.ts";

type Props = {
    appUser: AppUser | undefined | null
}

export default function NewUser(props: Readonly<Props>) {
    return (
        <div className={"col-12"}>
            {<NewMealForm appUser={props.appUser}/>}
        </div>
    )

}