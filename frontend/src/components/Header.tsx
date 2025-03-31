import NerdNavigation from "./NerdNavigation.tsx";
import NerdButton from "./forms/NerdButton.tsx";
import LoginIcon from "@mui/icons-material/Login";

const LoginButtonStyle = { color:"#394738", borderColor: "#394738", marginRight: "30px" }
const ButtonStyleLightFull = { backgroundColor: "#eeede9", color: "#394738", width: "200px" }
//const ButtonStyleDarkFull = { backgroundColor: "#394738", color: "#eeede9", width: "200px" }
const ButtonStyleOrangeFull = { backgroundColor: "#f68247", color: "#eeede9", width: "200px" }

export default function Header() {
    return (
        <>
                <div className={"row pt-2"}>
                    <div className={"col-md-2 col-lg-1"}>
                        <NerdNavigation />
                    </div>
                    <div className={"col-md-10 col-lg-6 align-content-end"}>

                        <h1 className={"headline headline-font"}>Super Nutrition Tracker</h1>
                    </div>
                    <div className={"col-md-12 col-lg-5 text-lg-end align-content-center"}>
                        <NerdButton buttonText={"Login"} styling={LoginButtonStyle } variant={"outlined"} startIcon={<LoginIcon/>} color={"info"} />
                    </div>
                </div>

            <div className={"row header-container"}>
                <div className={"col-md-12 col-lg-6 header-left text-lg-start"}>
                    <h2>Track your Nutrition</h2>
                    <NerdButton buttonText={"test"} styling={ButtonStyleLightFull} variant={"contained"} cssClasses="mt-3" />
                </div>
                <div className={"col-md-12 col-lg-6 header-right text-lg-start"}>
                    <h2>Dein Grundumsatz pro Tag</h2>

                    <p className={"font-lg mb-0"}>2564 kcal</p>
                    <p >Alter: 43 &bull; Gewicht: 80 kg &bull; Größe: 183 cm</p>

                    <NerdButton buttonText={"test2"} styling={ ButtonStyleOrangeFull } variant={"contained"} />
                </div>
            </div>
        </>
    )
}