import {Drawer, IconButton, List, ListItem, ListItemText, Typography} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import * as React from "react";
import {useState} from "react";
import CloseIcon from "@mui/icons-material/Close";
import {NavigateFunction, useNavigate} from "react-router-dom";

export default function NerdNavigation() {

    const [open, setOpen] = useState(false);
    const navigate:NavigateFunction = useNavigate()

    const toggleDrawer = (isOpen: boolean) => (event: React.KeyboardEvent | React.MouseEvent) => {
        if ("key" in event && (event.key === "Tab" || event.key === "Shift")) {
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
                <ListItem
                    onClick={() => {
                        navigate("/")
                        setOpen(false)
                    }}
                    sx={{
                        color: "#f68247",
                        textDecoration: "none",
                        "&:hover": { backgroundColor: "#dbdbdb" },
                    }}
                >
                    <ListItemText primary="Startseite" />
                </ListItem>
                <ListItem
                    onClick={() => {
                        navigate("/meal/new")
                        setOpen(false)
                    }
                }
                    sx={{
                        color: "#f68247",
                        textDecoration: "none",
                        "&:hover": { backgroundColor: "#dbdbdb" },
                    }}
                >
                    <ListItemText primary="Mahlzeit oder Produkt hinzufügen" />
                </ListItem>
                <ListItem
                    onClick={() => {
                        navigate("/meal/overview")
                        setOpen(false)
                    }
                    }
                    sx={{
                        color: "#f68247",
                        textDecoration: "none",
                        "&:hover": { backgroundColor: "#dbdbdb" },
                    }}
                >
                    <ListItemText primary="Details anzeigen" />
                </ListItem>
                <ListItem
                    onClick={() => {
                        navigate("/user/new")
                        setOpen(false)
                    }}
                    sx={{
                        color: "#f68247",
                        textDecoration: "none",
                        "&:hover": { backgroundColor: "#dbdbdb" },
                    }}
                >
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