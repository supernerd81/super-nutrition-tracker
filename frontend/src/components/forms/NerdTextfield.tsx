import {InputAdornment, TextField, TextFieldVariants} from "@mui/material";
import * as React from "react";

type Props = {
    id: string,
    label: string,
    defaultValue: string
    variant: TextFieldVariants | undefined
    fieldWidth: string
    marginRight: string
    required?: boolean | undefined
    endAdorment?: string | null
    value?: string | null
    onChange?: (event: React.ChangeEvent<HTMLInputElement>) => void,
    onBlur?: (event: React.FocusEvent<HTMLInputElement>) => void,
    name: string,
    type: string
}

export default function NerdTextfield(props: Readonly<Props>) {
    return (
        <TextField
            required={props.required}
            id={props.id}
            value={props.value}
            label={props.label}
            name={props.name}
            onChange={props.onChange}
            onBlur={props.onBlur ?? undefined}
            InputProps={{
                endAdornment: props.endAdorment ? (
                    <InputAdornment position="end">{props.endAdorment}</InputAdornment>
                ) : undefined
            }}
            defaultValue={props.defaultValue}
            variant={props.variant}
            type={props.type}
            sx={ {
                width: props.fieldWidth,
                marginRight: props.marginRight,
                "& .MuiInput-underline:hover:before": { borderBottom: "1px solid #f68247" }, // Hover-Farbe
                "& .MuiInput-underline:after": { borderBottom: "2px solid #f68247" }, // Fokus-Farbe
                "& .MuiInputLabel-root.Mui-focused": { color: "#f68247" }, // Label-Farbe bei Fokus
            }}
        />
    )
}