import {InputAdornment, TextField, TextFieldVariants} from "@mui/material";

type Props = {
    id: string,
    label: string,
    defaultValue: string
    variant: TextFieldVariants | undefined
    fieldWidth: string
    marginRight: string
    required?: boolean | undefined
    endAdorment?: string | null
}

export default function NerdTextfield(props: Readonly<Props>) {
    return (
        <TextField
            required={props.required}
            id={props.id}
            label={props.label}
            {...(props.endAdorment ? { slotProps: {
                input: {
                    endAdornment: <InputAdornment position="end">{props.endAdorment}</InputAdornment>,
                },
            }
            } : {})}
            defaultValue={props.defaultValue}
            variant={props.variant}
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