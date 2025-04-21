import {FormControl, InputLabel, MenuItem, Select, SelectChangeEvent} from "@mui/material";

type Props = {
    id: string,
    label: string,
    labelId: string,
    fieldWidth: string,
    variant: "standard" | "outlined" | "filled" | undefined,
    value: string | undefined
    onChange: (event: SelectChangeEvent) => void,
    name: string
}

export default function NerdSelectField(props: Readonly<Props>) {

    return (<FormControl variant={ props.variant }  className={"nerd-textfield"} sx={{
        width: props.fieldWidth,
        textAlign: "left",
        "& .MuiInputLabel-root": {
            color: "rgba(0, 0, 0, 0.6)",
        },
        "& .MuiInputLabel-root.Mui-focused": {
            color: "#f68247",
        },
    }}>
    <InputLabel id={ props.labelId }>Gender</InputLabel>
        <Select
            className={"nerd-textfield"}
            labelId={ props.labelId}
            id={ props.id }
            value={props.value}
            label={ props.label }
            name={props.name}
            onChange={props.onChange}
            sx={{
                width: props.fieldWidth,
            }}
            >
            <MenuItem value={"MALE"} >Male</MenuItem>
            <MenuItem value={"FEMALE"}>Female</MenuItem>
        </Select>
        </FormControl>)
}