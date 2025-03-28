import {Box, InputAdornment, TextField, Typography} from "@mui/material";

import '../../css/forms.css'
import {LocalizationProvider} from "@mui/x-date-pickers";
import {DatePicker} from "@mui/x-date-pickers/DatePicker";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";
import {Dayjs} from "dayjs";
import SaveIcon from '@mui/icons-material/Save';
import Button from '@mui/material/Button';
import {useState} from "react";


export default function NewUserForm() {

    const [selectedDate, setSelectedDate] = useState<Dayjs | null>(null);
    const [error, setError] = useState(false);

    const handleSubmit = (event: React.FormEvent) => {
        event.preventDefault();

        if (!selectedDate) {
            setError(true); // Fehler anzeigen, wenn kein Datum gewählt wurde
        } else {
            setError(false);
            console.log("Formular abgeschickt mit Datum:", selectedDate.format("DD.MM.YYYY"));
        }
    };

    return (
        <Box
            component={"form"}
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
                <h3 style={{ marginBottom: "20px" }}>Neues Profil anlegen</h3>

                <p style={{ fontSize: "16px", marginTop: "10px" }}>Für die Berechnung des Grundumsatzes (kcal) pro Tag sind diese Angaben notwendig! Fülle deshalb das Formular vollständig aus und klicke anschließend auf speichern.</p>
        </Typography>

            <div style={{ marginBottom: "20px" }}>
                <TextField
                    required
                    id={"firstname"}
                    label={"Vorname"}
                    defaultValue={""}
                    variant={"standard"}
                    sx={ {
                        width: "300px !important",
                        marginRight: "20px",
                        "& .MuiInput-underline:hover:before": { borderBottom: "1px solid #f68247" }, // Hover-Farbe
                        "& .MuiInput-underline:after": { borderBottom: "2px solid #f68247" }, // Fokus-Farbe
                        "& .MuiInputLabel-root.Mui-focused": { color: "#f68247" }, // Label-Farbe bei Fokus
                    }}
                    />

                <TextField
                    required
                    id={"lastname"}
                    label={"Nachname"}
                    defaultValue={""}
                    variant={"standard"}
                    sx={ {
                        width: "310px !important",
                        marginRight: "10px",
                        "& .MuiInput-underline:hover:before": { borderBottom: "1px solid #f68247" }, // Hover-Farbe
                        "& .MuiInput-underline:after": { borderBottom: "2px solid #f68247" }, // Fokus-Farbe
                        "& .MuiInputLabel-root.Mui-focused": { color: "#f68247" }, // Label-Farbe bei Fokus
                    }}
                /></div><div>

            {/* DatePicker mit Pflichtfeld-Validierung */}
            <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DatePicker
                    label="Geburtsdatum *"
                    value={selectedDate}
                    onChange={(newValue: Dayjs | null) => setSelectedDate(newValue)}
                    format="DD.MM.YYYY"
                    slotProps={{
                        textField: {
                            variant: "standard",
                            fullWidth: true,
                            error: error, // Fehlerzustand setzen
                            helperText: error ? "Bitte ein Datum auswählen!" : "",
                            sx: {
                                width: "300px !important",
                                marginRight: "20px",
                                "& .MuiInput-underline:hover:before": { borderBottom: "1px solid #f68247" }, // Hover-Farbe
                                "& .MuiInput-underline:after": { borderBottom: "2px solid #f68247" }, // Fokus-Farbe
                                "& .MuiInputLabel-root.Mui-focused": { color: "#f68247" }, // Label-Farbe bei Fokus
                            },
                        },
                    }}
                />
            </LocalizationProvider>

            <TextField
                required
                id={"userweight"}
                label={"Gewicht"}
                slotProps={{
                    input: {
                        endAdornment: <InputAdornment position="end">kg</InputAdornment>,
                    },
                }}
                defaultValue={""}
                variant={"standard"}
                sx={ {
                    width: "150px !important",
                    marginRight: "15px",
                    "& .MuiInput-underline:hover:before": { borderBottom: "1px solid #f68247" }, // Hover-Farbe
                    "& .MuiInput-underline:after": { borderBottom: "2px solid #f68247" }, // Fokus-Farbe
                    "& .MuiInputLabel-root.Mui-focused": { color: "#f68247" }, // Label-Farbe bei Fokus
                }}
            />
            <TextField
                required
                id={"userweight"}
                label={"Größe"}
                slotProps={{
                    input: {
                        endAdornment: <InputAdornment position="end">cm</InputAdornment>,
                    },
                }}
                defaultValue={""}
                variant={"standard"}
                sx={ {
                    width: "140px !important",
                    "& .MuiInput-underline:hover:before": { borderBottom: "1px solid #f68247" }, // Hover-Farbe
                    "& .MuiInput-underline:after": { borderBottom: "2px solid #f68247" }, // Fokus-Farbe
                    "& .MuiInputLabel-root.Mui-focused": { color: "#f68247" }, // Label-Farbe bei Fokus
                }}
            />
            </div>
            <div className={"mt-4"} style={{ display: "flex", justifyContent: "flex-end", width: "100%", paddingRight: "25px" }}>
                <Button variant="contained" endIcon={<SaveIcon />} style={{ backgroundColor: "#f68247" }} >
                    Speichern
                </Button>
            </div>
        </Box>
    )
}