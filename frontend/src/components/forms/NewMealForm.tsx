import {Box, Typography} from "@mui/material";

import '../../css/forms.css'
import '../../css/DateTime.css'
import {DateTimePicker, LocalizationProvider} from "@mui/x-date-pickers";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";
import dayjs, {Dayjs} from "dayjs";
import SaveIcon from '@mui/icons-material/Save';
import Button from '@mui/material/Button';
import {useState} from "react";
import NerdTextfield from "./NerdTextfield.tsx";


export default function NewMealForm() {

    const [selectedDateTime, setSelectedDateTime] = useState<Dayjs | null>(dayjs()); // Standardwert ist das aktuelle Datum & Uhrzeit
    const [error, setError] = useState(false);

    const handleSubmit = (event: React.FormEvent) => {
        event.preventDefault();

        if (!selectedDateTime) {
            setError(true); // Fehler anzeigen, wenn kein Datum/Uhrzeit gewählt wurde
        } else {
            setError(false);
            console.log("Formular abgeschickt mit Datum & Uhrzeit:", selectedDateTime.format("DD.MM.YYYY HH:mm"));
        }
    };

    const [mealData, setMealData] = useState({

        // firstname: (props?.appUser?.firstname === undefined ? "" : props.appUser.firstname),
        // lastname: (props.appUser?.lastname === undefined ? "" : props.appUser.lastname),
        // gender: props.appUser?.gender ?? "",
        // userweight: props.appUser?.weight,
        // userheight: props.appUser?.height
    })

    function handleInputChange(event: React.ChangeEvent<HTMLInputElement>) {
        const {name, value} =  event.target
        setMealData((prevData) => ({
            ...prevData, [name]: value,
        }))
    }

    return (
        <Box
            component={"form"}
            onSubmit={handleSubmit}
            sx={{
                display: "flex",
                flexDirection: "column",
                alignItems: "flex-start", // Links ausrichten
                width: "650px", // Formularbreite
                margin: "auto", // Ganze Box zentrieren
            }}
            className={"mt-5 "}
        >
            <Typography
                variant="h6"
                sx={{
                    marginBottom: "10px",
                    textAlign: "left", // Links ausgerichtet
                }}
            >
                <h3 style={{ marginBottom: "18px" }}>Mahlzeit oder Produkt eingeben</h3>

                <p style={{ fontSize: "16px", marginTop: "10px" }}>Gib eine neue Mahlzeit ein. Die kcal werden automatisch aus Kohlenhydrate, Proteine und Fett berechnet. Wenn Du das Formular vollständig ausgefüllt hast, klicke auf Speichern.</p>
                <p style={{ fontSize: "14px", marginTop: "10px", color: "#f68247" }}><b>Hinweis:</b> Wenn Du ein Produkt mit Barcode hast, kannst Du den Barcode eingeben oder scannen. Das Produkt wird dann automatisch geladen und das Formular ausgefüllt, wenn es bereits in der Datenbank ist.</p>
            </Typography>

            <div style={{ marginBottom: "20px" }}>
                <NerdTextfield id={"barcode"} name={"barcode"} onChange={handleInputChange} type={"text"} label={"Barcode"} defaultValue={""} variant={"standard"} fieldWidth={"620px !important"} marginRight={"20px"} required={false} value={""}/>
            </div><div style={{ marginBottom: "20px" }}>

            <NerdTextfield id={"mealname"} name={"mealname"} type={"text"} onChange={handleInputChange} label={"Mahlzeit- / Produktname"} defaultValue={""} variant={"standard"} fieldWidth={"300px !important"} marginRight={"20px"} required={true} />
            <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DateTimePicker
                    label="Datum & Uhrzeit *"
                    value={selectedDateTime}
                    onChange={(newValue: Dayjs | null) => {
                        setSelectedDateTime(newValue);
                        setError(!newValue);
                    }}
                    format="DD.MM.YYYY HH:mm"
                    slotProps={{
                        textField: {
                            variant: "standard",
                            fullWidth: true,
                            error: error,
                            helperText: error ? "Bitte ein Datum und eine Uhrzeit auswählen!" : "",
                            sx: {
                                width: "300px !important",
                                marginRight: "20px",
                                "& .MuiInput-underline:hover:before": { borderBottom: "1px solid #f68247" },
                                "& .MuiInput-underline:after": { borderBottom: "2px solid #f68247" },
                                "& .MuiInputLabel-root.Mui-focused": { color: "#f68247" },
                            },
                        },
                        day: {
                            sx: {
                                "&.Mui-selected": {
                                    backgroundColor: "#f68247 !important",
                                    color: "white !important",
                                },
                                "&:hover": {
                                    backgroundColor: "rgba(230, 92, 30, 0.1) !important",
                                },
                            },
                        },
                    }}

                />
            </LocalizationProvider></div><div>

            <NerdTextfield id={"protein"} name={"protein"} onChange={handleInputChange} type={"number"} label={"Protein"} defaultValue={""} variant={"standard"} fieldWidth={"195px !important"} marginRight={"15px"} endAdorment={"g"} required={true} />
            <NerdTextfield id={"carbohydrates"} name={"carbohydrates"} onChange={handleInputChange} type={"number"} label={"Kohlenhydrate"} defaultValue={""} variant={"standard"} fieldWidth={"195px !important"} marginRight={"15px"} required={true} endAdorment={"g"} />
            <NerdTextfield id={"fat"} name={"fat"} onChange={handleInputChange} type={"number"} label={"Fett"} defaultValue={""} variant={"standard"} fieldWidth={"200px !important"} marginRight={""} required={true} endAdorment={"g"} />

        </div>
            <div className={"mt-4"} style={{ display: "flex", justifyContent: "flex-end", width: "100%", paddingRight: "25px" }}>
                <Button type="submit" variant="contained" endIcon={<SaveIcon />} style={{ backgroundColor: "#f68247" }} >
                    Speichern
                </Button>
            </div>
        </Box>
    )
}