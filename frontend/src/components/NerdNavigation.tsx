import {Drawer, IconButton, List, ListItem, ListItemText, Typography} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import {useState} from "react";
import CloseIcon from "@mui/icons-material/Close";
import {useNavigate} from "react-router-dom";

export default function NerdNavigation() {

    const [open, setOpen] = useState(false);
    const navigate = useNavigate()

    const toggleDrawer = (isOpen) => (event) => {
        if (event.type === "keydown" && (event.key === "Tab" || event.key === "Shift")) {
            return;
        }
        setOpen(isOpen);
    };

    const DrawerList = (
        <div style={{ width: 320, padding: "16px" }}>
            {/* Header mit Schließen-Button */}
            <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", padding: "16px" }}>
                <Typography variant="h6" sx={{ fontWeight: "bold" }}>
                    Navigation
                </Typography>
                <IconButton onClick={toggleDrawer(false)} aria-label="close menu">
                    <CloseIcon />
                </IconButton>
            </div>
        <List sx={{ cursor: "pointer" }}>
            <ListItem button onClick={() => {
                navigate("/");
                setOpen(false)
            }}>
                <ListItemText primary="Startseite"  />
            </ListItem>
            <ListItem button onClick={() => {
                navigate("/meal/new");
                setOpen(false)
            }}>
                <ListItemText primary="Mahlzeit oder Snack eingeben" />
            </ListItem>
            <ListItem button onClick={() => {
                navigate("/user/new")
                setOpen(false)
            }}>
                <ListItemText primary="Profildaten bearbeiten" />
            </ListItem>
        </List>
        </div>
    );

    return (
        <>
            <IconButton onClick={toggleDrawer(true)} aria-label="open menu">
                <MenuIcon  sx={{ fontSize: 40 }} />
            </IconButton>
            {/* Drawer Navigation */}
            <Drawer anchor="left" open={open} onClose={toggleDrawer(false)} sx={{ "& .MuiDrawer-paper": { backgroundColor: "#eeede9", color: "#f68247" } }}>
                {DrawerList}
            </Drawer>
        </>
    )
}