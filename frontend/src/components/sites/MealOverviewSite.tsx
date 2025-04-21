import {AppUser} from "../model/AppUser.ts";
import * as React from "react";
import {useEffect, useState} from "react";

import {DataGrid, GridActionsCellItem, GridColDef, GridRowParams} from '@mui/x-data-grid';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import axios from "axios";
import {MealOverviewData} from "../model/MealOverviewData.ts";
import {Dialog, DialogActions, DialogContent, DialogTitle} from "@mui/material";
import Button from "@mui/material/Button";
import NerdTextfield from "../forms/NerdTextfield.tsx";
import {DateTimePicker, LocalizationProvider} from "@mui/x-date-pickers";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";
import dayjs, {Dayjs} from "dayjs";

type Props = {
    appUser: AppUser | undefined | null
}

export default function MealOverviewSite(props: Readonly<Props>) {
    const [rows, setRows] = React.useState<MealOverviewData[]>([]);

    const fetchDailyMealData = async () => {

        axios.get("/api/meal/overview/today")
            .then(r => {
                setRows(r.data)
                console.log(r.data)
            })
            .catch(e => {
                console.error('Error fetching Overview Data: ', e)
            })
    }

    useEffect(() => {
        fetchDailyMealData().then(r => console.log(r))
    }, [props.appUser]);

    const handleDelete = (id: string) => {
        axios.delete(`/api/meal/delete/${id}`)
            .then(r => {
                fetchDailyMealData().then(r => console.log(r))
                console.log(r.data)
            })
            .catch(e => {
                console.error('Error deleting Meal: ', e)
            }
            )
    };

    const [editingRow, setEditingRow] = React.useState<MealOverviewData | null>(null);
    const [selectedDateTime, setSelectedDateTime] = useState<Dayjs | null>(dayjs())
    const [error, setError] = useState(false);


    function handleInputChange(event: React.ChangeEvent<HTMLInputElement>) {
        const { name, value } = event.target;

        setEditingRow((prevData) => {
            if (!prevData) return prevData; // oder eventuell ein leeres Objekt zurückgeben?
            return {
                ...prevData,
                [name]: value,
            };
        });
    }

    const handleEdit = (id: string) => {
        const row = rows.find(r => r.id === id);
        if (row) {
            setEditingRow(row);
            setSelectedDateTime(row.dateTime ? dayjs(row.dateTime) : dayjs())

            console.log(row)
        }
    };

    const handleEditSave = () => {
        if (editingRow) {

            axios.put(`/api/meal/update/${editingRow.id}`, editingRow)
                .then(r => {
                    fetchDailyMealData().then(r => console.log(r))

                    setEditingRow(null);
                    console.log(r.data)
                })
                .catch(e => {
                    console.error('Error updating Meal: ', e)
                }
                )
        }
    };

    const handleEditCancel = () => {
        setEditingRow(null);
    };

    const columns: GridColDef[] = [
        { field: 'dateTime', headerName: 'Datum / Uhrzeit', width: 200,
            renderCell: (params) => {
            const date = new Date(params.value);
            return new Intl.DateTimeFormat('de-DE', {
                    dateStyle: "medium",
                    timeStyle: "short",
                }).format(date)
            }},
        { field: 'name', headerName: 'Mahlzeit / Produkt', width: 200 },
        { field: 'fat', headerName: 'Fett (g)', width: 100 },
        { field: 'carbohydrates', headerName: 'Kohlenhydrate (g)', width: 150 },
        { field: 'protein', headerName: 'Protein (g)', width: 150 },
        {
            field: 'actions',
            type: 'actions',
            headerName: 'Aktionen',
            width: 100,
            getActions: (params: GridRowParams) => [
                <GridActionsCellItem key={"edit"} icon={<EditIcon />} label="Bearbeiten" onClick={() => handleEdit(params.id as string)} sx={{ color: "#799a61" }} />,
                <GridActionsCellItem key={"delete"} icon={<DeleteIcon />} label="Löschen" onClick={() => handleDelete(params.id as string)} sx={{ color: "#f68247" }} />,
            ],
        },
    ];

    return (
        <div style={{ height: 500, width: '100%' }} className={"pt-3"}>
            <DataGrid
                rows={rows}
                columns={columns}
                sortingOrder={['desc', 'asc']}
                paginationModel={{ pageSize: 5, page: 0 }}
                disableRowSelectionOnClick
                processRowUpdate={(updatedRow) => {
                    setRows((prevRows) =>
                        prevRows.map((row) => (row.id === updatedRow.id ? updatedRow : row))
                    );
                    return updatedRow;
                }}
            />

            <Dialog open={!!editingRow} onClose={handleEditCancel}>
                <DialogTitle>Mahlzeit bearbeiten</DialogTitle>
                <DialogContent>
                        <NerdTextfield id={"mealName"} name={"mealName"} type={"text"}   label={"Mahlzeit- / Produktname"} defaultValue={""} variant={"standard"} fieldWidth={"530px !important"} marginRight={"20px"} required={true} value={editingRow ? editingRow.name : ""} />
                    <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <DateTimePicker
                    name={"dateTime"}
                    label="Datum & Uhrzeit *"
                    value={selectedDateTime}
                    onChange={(newValue: Dayjs | null) => {
                    setSelectedDateTime(newValue)
                    setError(!newValue)

                        setEditingRow(prev => {
                            if (!prev || !newValue) return prev;
                            return {
                                ...prev,
                                dateTime: newValue.format("YYYY-MM-DDTHH:mm:ss")
                            }
                        });
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
                </LocalizationProvider>

        <NerdTextfield id={"protein"} onChange={handleInputChange} name={"protein"} type={"number"} label={"Protein"} defaultValue={""} variant={"standard"} fieldWidth={"195px !important"} marginRight={"15px"} endAdorment={"g"} required={true} value={ editingRow ? editingRow.protein.toString() : ""} />
        <NerdTextfield id={"carbohydrates"} onChange={handleInputChange} name={"carbohydrates"} type={"number"} label={"Kohlenhydrate"} defaultValue={""} variant={"standard"} fieldWidth={"195px !important"} marginRight={"15px"} required={true} endAdorment={"g"} value={ editingRow ? editingRow.carbohydrates.toString(): "" } />
        <NerdTextfield id={"fat"} name={"fat"} onChange={handleInputChange} type={"number"} label={"Fett"} defaultValue={""} variant={"standard"} fieldWidth={"200px !important"} marginRight={""} required={true} endAdorment={"g"} value={editingRow ? editingRow.fat.toString() : ""} />

                </DialogContent>
                <DialogActions>
                    <Button onClick={handleEditCancel}>Abbrechen</Button>
                    <Button onClick={handleEditSave} variant="contained" sx={{ backgroundColor: "#f68247",
                        "&:hover": {
                            backgroundColor: "#d75d1f"
                        }
                    }
                    }>Speichern</Button>

                </DialogActions>
            </Dialog>
        </div>
 )
}
