import {Drawer, IconButton, List, ListItem, ListItemText, Typography} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import {useState} from "react";

export default function NerdNavigation() {

    const [open, setOpen] = useState(false);

    const toggleDrawer = (isOpen) => (event) => {
        if (event.type === "keydown" && (event.key === "Tab" || event.key === "Shift")) {
            return;
        }
        setOpen(isOpen);
    };

    const DrawerList = (
        <div style={{ width: 320, padding: "16px" }}>
        <Typography variant="h6" sx={{ marginBottom: 2, fontWeight: "bold" }}>
            Navigation
        </Typography>
        <List sx={{ cursor: "pointer" }}>
            <ListItem button>
                <ListItemText primary="Startseite" />
            </ListItem>
            <ListItem button>
                <ListItemText primary="Mahlzeit oder Snack eingeben" />
            </ListItem>
            <ListItem button>
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