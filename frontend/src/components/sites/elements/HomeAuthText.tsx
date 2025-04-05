import LoginIcon from "@mui/icons-material/Login";
import NerdButton from "../../forms/NerdButton.tsx";

const LoginButtonStyle = { color:"#394738", borderColor: "#394738", marginRight: "30px" }
export default function HomeAuthText() {

    function login() {
        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin
        window.open(host + '/oauth2/authorization/github', '_self')
    }

    return <><p style={{ fontSize: "16pt" }}>
            ... wie schön, dass Du da bist,<br />
            doch wer Du genau bist, bleibt noch ein Rätsel, Mist!<br />
            Willst Du wissen, was Du isst, ob Dein Plan auch wirklich sitzt?<br />
            Dann logg Dich ein – sonst bleibt’s ein Ratespiel mit Witz.<br /><br />

            Der Tracker wär so gerne schlau,
            doch ohne Login bleibt alles grau.
            Kein Name, kein Verlauf, kein Ziel –
            für Datenfreunde nicht sehr viel.<br /><br />

            Mit einem Klick – ganz ohne Frust –
            bekommst Du Zugriff, wenn Du musst.
            Dann zeigt Dir unser System List:
            Ganz genau, was Du so isst – und was Du manchmal auch vergisst!
        </p>
    <NerdButton buttonText={"Login"} styling={LoginButtonStyle } variant={"outlined"} startIcon={<LoginIcon/>} color={"info"} onClick={login}/>
    </>
}