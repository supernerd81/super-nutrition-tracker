import HomeWelcomeText from "./elements/HomeWelcomeText.tsx";
import HomeAuthText from "./elements/HomeAuthText.tsx";
import {useEffect, useState} from "react";
import {AppUser} from "../model/AppUser.ts";
import NerdMealsToday from "./elements/NerdMealsToday.tsx";

type Props = {
    appUser: AppUser | undefined | null
}

export default function HomeSite(props: Readonly<Props>) {

    const [greeting, setGreeting] =  useState("Hallo");

    useEffect(() => {
        const hour = new Date().getHours();
        if(hour >= 5 && hour < 11) setGreeting("Guten Morgen");
        else if(hour >= 11 && hour < 18) setGreeting("Guten Tag");
        else setGreeting("Guten Abend");
    }, [])

    const [metabolicRate, setMetabolicRate] = useState<number >(2680)
    useEffect(() => {
        if(props.appUser?.metabolicRate !== undefined) {
            setMetabolicRate(Math.floor(props.appUser.metabolicRate))
        }
    }, [props.appUser?.metabolicRate]);

    return (
        <div className={"row "}>

            <div className={"col-md-12 col-lg-6 text-start p-5"}>

                <h3 className={"mb-4"}>{greeting}, {props.appUser?.firstname ?? "Unbekannt"},</h3>

                { props.appUser === undefined ? <HomeAuthText /> : <HomeWelcomeText/> }

            </div>
            <div className={"col-md-12 col-lg-6 p-4 mt-3"}>

                <NerdMealsToday appUser={props.appUser} metabolicRate={metabolicRate} />
            </div>

        </div>
    )
}