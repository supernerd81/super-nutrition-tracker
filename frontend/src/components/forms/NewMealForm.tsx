import {Box, Typography} from "@mui/material";

import '../../css/forms.css'
import '../../css/DateTime.css'
import {ClearIcon, DateTimePicker, LocalizationProvider} from "@mui/x-date-pickers";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";
import dayjs, {Dayjs} from "dayjs";
import SaveIcon from '@mui/icons-material/Save';
import Button from '@mui/material/Button';
import {useState} from "react";
import NerdTextfield from "./NerdTextfield.tsx";
import axios from "axios";
import {NewMeal} from "../model/NewMeal.ts";
import {AppUser} from "../model/AppUser.ts";
import Loader from "../utils/Loader.tsx";
import NerdSnackbar from "./NerdSnackbar.tsx";

type Props = {
    appUser: AppUser | undefined | null
}

export default function NewMealForm(props: Readonly<Props>) {

    const [selectedDateTime, setSelectedDateTime] = useState<Dayjs | null>(dayjs()); // Standardwert ist das aktuelle Datum & Uhrzeit
    const [error, setError] = useState(false);
    const [isLoading, setIsLoading] = useState(true)

    const [mealData, setMealData] = useState({
        barcode: "",
        mealName: "",
        protein: "",
        carbohydrates: "",
        fat: ""
    })

    const [openSnackbar, setOpenSnackbar] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState("");
    const [snackbarSeverity, setSnackbarSeverity] = useState<"success" | "error">("success")

    function resetForm() {
        setMealData(prev => ({
            ...prev,
            barcode: "",
            mealName: "",
            protein: "",
            carbohydrates: "",
            fat: ""
        }))
    }

    function handleInputChange(event: React.ChangeEvent<HTMLInputElement>) {
        const {name, value} =  event.target
        setMealData((prevData) => ({
            ...prevData, [name]: value,
        }))
    }

    const handleBarcodeBlur = async (event: React.FocusEvent<HTMLInputElement>) => {
        const barcode = event.target.value
        if(!barcode) return;

        try {
            const response = await axios.get(`/api/meal/barcode/${barcode}`)
            const data = response.data

            setMealData(prev => ({
                ...prev,
                mealName: data.mealName || "",
                protein: data.protein?.toString() || "",
                carbohydrates: data.carbohydrates?.toString() || "",
                fat: data.fat?.toString() || ""
            }))

            setSnackbarMessage("Mahlzeit bzw. wurde automatisch geladen!")
            setSnackbarSeverity("success")
            setOpenSnackbar(true)
        } catch (error) {
            console.log("Produkt nicht gefunden: ", error)
            setSnackbarMessage("Kein Produkt mit diesem Barcode gefunden! Mahlzeit bzw. Produkt wird neu angelegt!")
            setSnackbarSeverity("success")
            setOpenSnackbar(true)
        }
    }

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        setIsLoading(false);

        if(!selectedDateTime) {
            setError(true);
            setIsLoading(true);
            setSnackbarMessage("Bitte Datum und Uhrzeit auswählen!");
            setSnackbarSeverity("error");
            setOpenSnackbar(true);
            return;
        } else {
            setError(false);

            try {
                const response = await axios.post<NewMeal>('/api/meal/new', {
                    userId: props.appUser?.id,
                    dateTime: selectedDateTime.format("YYYY-MM-DDTHH:mm:ss"),
                    barcode: mealData.barcode,
                    mealName: mealData.mealName,
                    protein: parseInt(mealData.protein),
                    carbohydrates: parseInt(mealData.carbohydrates),
                    fat: parseInt(mealData.fat)
                })


                if (response) {
                    setSnackbarMessage("Speichern erfolgreich!");
                    setSnackbarSeverity("success");
                    setOpenSnackbar(true);

                    // 🧹 Formular zurücksetzen
                    setMealData({
                        barcode: "",
                        mealName: "",
                        protein: "",
                        carbohydrates: "",
                        fat: ""
                    });
                    setSelectedDateTime(dayjs())
                }

            } catch(error) {
                console.error("Fehler beim Speichern: ", error);
                setSnackbarMessage("Fehler beim Speichern. Bitte versuche es später erneut.");
                setSnackbarSeverity("error");
                setOpenSnackbar(true);
            } finally {
                setIsLoading(true)
            }
        }
    }

    if(!isLoading) {
        return <Loader />
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

            <NerdSnackbar
                openSnackbar={openSnackbar}
                setOpenSnackbar={setOpenSnackbar}
                snackbarMessage={snackbarMessage}
                snackbarSeverity={snackbarSeverity}
            />

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
                <NerdTextfield id={"barcode"} name={"barcode"} onBlur={handleBarcodeBlur} onChange={handleInputChange} type={"text"} label={"Barcode"} defaultValue={""} variant={"standard"} fieldWidth={"620px !important"} marginRight={"20px"} required={false} value={mealData.barcode}/>
            </div><div style={{ marginBottom: "20px" }}>

            <NerdTextfield id={"mealName"} name={"mealName"} type={"text"}  onChange={handleInputChange} label={"Mahlzeit- / Produktname"} defaultValue={""} variant={"standard"} fieldWidth={"300px !important"} marginRight={"20px"} required={true} value={mealData.mealName} />
            <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DateTimePicker
                    name={"dateTime"}
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

            <NerdTextfield id={"protein"} name={"protein"} onChange={handleInputChange} type={"number"} label={"Protein"} defaultValue={""} variant={"standard"} fieldWidth={"195px !important"} marginRight={"15px"} endAdorment={"g"} required={true} value={mealData.protein} />
            <NerdTextfield id={"carbohydrates"} name={"carbohydrates"} onChange={handleInputChange} type={"number"} label={"Kohlenhydrate"} defaultValue={""} variant={"standard"} fieldWidth={"195px !important"} marginRight={"15px"} required={true} endAdorment={"g"} value={mealData.carbohydrates} />
            <NerdTextfield id={"fat"} name={"fat"} onChange={handleInputChange} type={"number"} label={"Fett"} defaultValue={""} variant={"standard"} fieldWidth={"200px !important"} marginRight={""} required={true} endAdorment={"g"} value={mealData.fat} />

        </div>
            <div className={"mt-4"} style={{ display: "flex", justifyContent: "flex-end", width: "100%", paddingRight: "25px" }}>
                <Button type="reset" onClick={resetForm} variant="contained" endIcon={<ClearIcon />} style={{ backgroundColor: "#b8b4ab", marginRight:"10px" }} >
                    Zurücksetzen
                </Button>

                <Button type="submit" variant="contained" endIcon={<SaveIcon />} style={{ backgroundColor: "#f68247" }} >
                    Speichern
                </Button>
            </div>
        </Box>
    )
}