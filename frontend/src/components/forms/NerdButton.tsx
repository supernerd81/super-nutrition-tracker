import {Button} from "@mui/material";
import * as React from "react";

type props = {
    buttonText: string
    variant?: "text" | "outlined" | "contained";
    styling: React.CSSProperties
    cssClasses?: string | undefined
    startIcon?: React.ReactNode
    color?: "primary" | "secondary" | "success" | "error" | "info" | "warning"
}

export default function NerdButton(props: Readonly<props>) {
    return (
        <>
            <Button variant={props.variant} {...(props.startIcon ? { startIcon: props.startIcon} : {})} {...(props.color ?  { color: props.color} : {})} style={ props.styling } className={props.cssClasses}>
                {props.buttonText}
            </Button>
        </>
    )
}