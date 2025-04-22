import {Box, SelectChangeEvent, Typography} from "@mui/material";

import '../../css/forms.css'
import '../../css/DateTime.css'
import {LocalizationProvider} from "@mui/x-date-pickers";
import {DatePicker} from "@mui/x-date-pickers/DatePicker";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";
import dayjs, {Dayjs} from "dayjs";
import SaveIcon from '@mui/icons-material/Save';
import Button from '@mui/material/Button';
import {useEffect, useState} from "react";
import NerdTextfield from "./NerdTextfield.tsx";
import NerdSelectField from "./NerdSelectField.tsx";
import {AppUser} from "../model/AppUser.ts";
import axios from "axios";
import Loader from "../utils/Loader.tsx";
import NerdSnackbar from "./NerdSnackbar.tsx";

type Props = {
    appUser: AppUser | undefined | null
    setAppUser: React.Dispatch<React.SetStateAction<AppUser | null | undefined>>
}

export default function UpdateProfileForm(props: Readonly<Props>) {

    const [selectedDate, setSelectedDate] = useState<Dayjs | null>(null);
    const [error, setError] = useState(false);
    const [isLoading, setIsLoading] = useState(true)

    const [profileData, setProfileData] = useState({

        firstname: props.appUser?.firstname ?? "",
        lastname: props.appUser?.lastname ?? "",
        gender: props.appUser?.gender ?? "",
        userweight: props.appUser?.weight ?? "",
        userheight: props.appUser?.height?.toString() ?? "",
        birthday: props.appUser?.birthday ?? "",

    })

    const [openSnackbar, setOpenSnackbar] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState("");
    const [snackbarSeverity, setSnackbarSeverity] = useState<"success" | "error">("success")

    function handleInputChange(event: React.ChangeEvent<HTMLInputElement>) {
        const {name, value} =  event.target
        setProfileData((prevData) => ({
            ...prevData, [name]: value,
        }))
    }

    function handleSelectChange(event: SelectChangeEvent) {
        const {name, value} =  event.target
        setProfileData((prevData) => ({
            ...prevData, [name]: value,
        }))
    }

    useEffect(() => {
        if(props.appUser?.birthday) {
            setSelectedDate(dayjs(props.appUser.birthday))
        }
    }, [props.appUser?.birthday]);

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        setIsLoading(false)

        if (!selectedDate) {
            setError(true)
            setIsLoading(true)
        } else {
            setError(false)

            try {
                const response = await axios.put<AppUser>(`/api/user/update/${props.appUser?.id}`, {
                    id: props.appUser?.id,
                    userid:props.appUser?.id,
                    firstname: profileData.firstname,
                    lastname: profileData.lastname,
                    birthday: selectedDate.format("YYYY-MM-DD"),
                    gender: profileData.gender,
                    weight: profileData.userweight,
                    height: profileData.userheight
                });

                console.log(response.data)
                props.setAppUser(response.data);
                setSnackbarMessage("Speichern erfolgreich!");
                setSnackbarSeverity("success");
                setOpenSnackbar(true);
            } catch (error) {
                console.error("Fehler beim Aktualisieren des Profils:", error);
                setSnackbarMessage("Fehler beim Speichern. Bitte versuche es später erneut.")
                setSnackbarSeverity("success")
                setOpenSnackbar(true)
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
                alignItems: "flex-start",
                width: "650px",
                maxWidth: "100%",
                margin: "auto",
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
                <h3 style={{ marginBottom: "18px" }}>Profil aktualisieren</h3>

                <p style={{ fontSize: "16px", marginTop: "10px" }}>Für die Berechnung des Grundumsatzes (kcal) pro Tag sind diese Angaben notwendig! Fülle deshalb das Formular vollständig aus und klicke anschließend auf speichern.</p>
        </Typography>

            <div style={{ marginBottom: "20px", width: "100%" }}>

                <NerdTextfield onChange={handleInputChange} type={"text"} name={"firstname"} id={"firstname"} label={"Vorname"} defaultValue={""} variant={"standard"} fieldWidth={"240px"} marginRight={"20px"} required={true} value={profileData.firstname} />
                <NerdTextfield onChange={handleInputChange} type={"text"} name={"lastname"} id={"lastname"} label={"Nachname"} defaultValue={""} variant={"standard"} fieldWidth={"250px"} marginRight={"20px"} required={true} value={profileData.lastname} />
                <NerdSelectField onChange={handleSelectChange} name={"gender"} id={"nerd-select-gender"} label={"Gender"} labelId={"nerd-select-gender-label"} fieldWidth={"100px"} variant={"standard" } value={profileData.gender} />
            </div><div>

            {/* DatePicker mit Pflichtfeld-Validierung */}
            <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DatePicker
                    label="Geburtsdatum *"
                    value={selectedDate}
                    onChange={(newValue: Dayjs | null) => setSelectedDate(newValue)}
                    format="DD.MM.YYYY"
                    className={"nerd-textfield"}
                    slotProps={{
                        textField: {
                            variant: "standard",
                            fullWidth: true,
                            error: error,
                            helperText: error ? "Bitte ein Datum auswählen!" : "",
                            sx: {
                                width: "300px ",
                                marginBottom: "10px",
                                marginRight: "20px",
                                "& .MuiInput-underline:hover:before": { borderBottom: "1px solid #f68247" },
                                "& .MuiInput-underline:after": { borderBottom: "2px solid #f68247" },
                                "& .MuiInputLabel-root.Mui-focused": { color: "#f68247" },
                            },
                        },
                    }}
                />
            </LocalizationProvider>

            <NerdTextfield onChange={handleInputChange} name={"userweight"} type={"number"} id={"userweight"} label={"Gewicht"} defaultValue={""} variant={"standard"} fieldWidth={"150px"} marginRight={"15px"} endAdorment={"kg"} required={true} value={profileData.userweight} />
            <NerdTextfield onChange={handleInputChange} name={"userheight"} type={"number"} id={"userheight"} label={"Größe"} defaultValue={""} variant={"standard"} fieldWidth={"140px"} marginRight={""} endAdorment={"cm"} required={true} value={profileData.userheight} />
            </div>
            <div className={"mt-4"} style={{ display: "flex", justifyContent: "flex-end", width: "100%", paddingRight: "25px" }}>
                <Button type="submit" variant="contained" endIcon={<SaveIcon />} style={{ backgroundColor: "#f68247" }} >
                    Speichern
                </Button>
            </div>
        </Box>
    )
}