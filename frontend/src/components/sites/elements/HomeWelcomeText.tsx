import {ListItemButton, ListItemText} from "@mui/material";
import {useNavigate} from "react-router-dom";

export default function HomeWelcomeText() {

    const navigate = useNavigate();

    return <>
        <p style={{ fontSize: "16pt" }}>wie schön, Dich zu seh’n!<br />
            Der Tracker ist startklar – es kann jetzt losgeh’n.
            Ob Mahlzeit, Produkt oder Profilgedanke,
            hier bist Du Chef – ganz ohne Schranke!<br /><br />

            Klick unten, was Du tun willst heut,
            der Tracker reagiert, sobald Dein Klick sich häuft.
            Ob Daten hinzufügen oder was an Dir feilen –
            die Tools sind da, Du musst nur verweilen!</p>

        <ListItemButton component="a" onClick={ () => {
            navigate("/meal/new")
        }} sx={{color: '#f68247'}}>
            <ListItemText primary="Mahlzeit oder Produkt hinzufügen" />
        </ListItemButton>

        <ListItemButton component="a"  sx={{color: '#f68247'}} onClick={ () =>
            navigate("/user/new")
        }>
            <ListItemText primary="Profildaten bearbeiten"  />
        </ListItemButton>
    </>
}