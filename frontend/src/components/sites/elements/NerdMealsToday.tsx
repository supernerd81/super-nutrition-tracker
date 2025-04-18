import {Gauge, gaugeClasses} from "@mui/x-charts";
import {AppUser} from "../../model/AppUser.ts";
import {Box, LinearProgress, ListItemButton, ListItemText} from "@mui/material";
import {useEffect, useState} from "react";
import axios from "axios";
import {TodayMetabolic} from "../../model/TodayMetabolic.ts";
import {useNavigate} from "react-router-dom";

type Props = {
    appUser: AppUser | undefined | null,
    metabolicRate: number
}

export default function NerdMealsToday(props: Readonly<Props>) {

    const [metabolicRatesTodayData, setMetabolicRatesTodayData] = useState<TodayMetabolic>()
    const navigate = useNavigate();
    const [fatPercent, setFatPercent] = useState<number>()
    const [carbohydratesPercent, setCarbohydratesPercent] = useState<number>()
    const [proteinPercent, setProteinPercent] = useState<number>()
    const [fatBoxColor, setFatBoxColor] = useState<string>("#df2727")
    const [proteinBoxColor, setProteinBoxColor] = useState<string>("#df2727")
    const [carbohydratesBoxColor, setCarbohydratesBoxColor] = useState<string>("#df2727")
    const [gaugeColor, setGaugeColor] = useState<string>()

    function mealToday() {
        if (!props.appUser?.id) return
        axios.get(`/api/meal/today/${props.appUser?.id}`)
            .then(r => {
                const metabolicRatesToday = r.data
                setMetabolicRatesTodayData(metabolicRatesToday)
            })
            .catch(error => {
                console.error('Error fetching User data: ', error)
            })

    }
    useEffect(mealToday, [props.appUser?.id])

    function macronutrients() {
        if (!props.appUser || !metabolicRatesTodayData) return;

        setFatPercent(props.appUser && metabolicRatesTodayData?.fatPercent !== undefined
            ? Math.min(metabolicRatesTodayData.fatPercent, 100)
            : 0)

        setCarbohydratesPercent(props.appUser && metabolicRatesTodayData?.carbohydratesPercent !== undefined
            ? Math.min(metabolicRatesTodayData.carbohydratesPercent, 100)
            : 0)

        setProteinPercent(props.appUser && metabolicRatesTodayData?.proteinPercent !== undefined
            ? Math.min(metabolicRatesTodayData.proteinPercent, 100)
            : 0)

        setFatBoxColor( (metabolicRatesTodayData?.fatPercent > 100 ? "#df2727" : "#799a61"))
        setProteinBoxColor( (metabolicRatesTodayData?.proteinPercent > 100 ? "#df2727" : "#799a61"))
        setCarbohydratesBoxColor( (metabolicRatesTodayData?.carbohydratesPercent > 100 ? "#df2727" : "#799a61") )
        setGaugeColor( (metabolicRatesTodayData?.kcalToday > props.metabolicRate ? "#df2727" : "#799a61") )
    }

    useEffect(macronutrients, [metabolicRatesTodayData])

    if (!metabolicRatesTodayData && props.appUser) {
        return <p>Daten werden geladen...</p>
    }

    return (
    <div className={"col-12 box1 pt-2"}>

        <div className={"row mb-3"}>
            <div className={"col-12"}>
                <h2 className={"fw-bold"}>Heute</h2>
            </div>
        </div>

        <div className={"row"}><div className={"col-12  d-flex justify-content-center"}>
            <Gauge
                height={200}
                width={400}
                value={ props.appUser === undefined ? 0 : metabolicRatesTodayData?.kcalToday }
                valueMax={props.metabolicRate}
                startAngle={-110}
                endAngle={110}
                cornerRadius="50%"
                sx={{
                    [`& .${gaugeClasses.valueText}`]: {
                        fontSize: 28,
                        transform: 'translate(0px, 0px)',
                    },
                    [`& .${gaugeClasses.valueArc}`]: {
                        fill: gaugeColor,
                    },
                }}
                text={
                    ({ value, valueMax }) => `${value} / ${valueMax}`
                }
            />
        </div></div>

        <div className={"row p-3"}>
            <div className={"col-4 p-3 "}>
                <p className={"mb-0 pt-0"}>Fett</p>
                <Box sx={{ width: '100%', fill: "#f68247" }}>
                    <LinearProgress variant="determinate" value={ (props.appUser === undefined || metabolicRatesTodayData?.fatPercent === undefined ? 0 : fatPercent )  }  sx={{
                        backgroundColor: '#e0e0e0',
                        '& .MuiLinearProgress-bar': {
                            backgroundColor: fatBoxColor,
                        },
                    }}/>
                </Box>
                <p>{metabolicRatesTodayData?.fatCurrent ?? 0} / {metabolicRatesTodayData?.fatMax ?? 0} g</p>

            </div>

            <div className={"col-4 p-3 "}>
                <p className={"mb-0 pt-0"}>KH</p>
                <Box sx={{ width: '100%', fill: "#f68247" }}>
                    {/* Setze einen statischen Wert für den Fortschritt */}
                    <LinearProgress variant="determinate" value={ props.appUser === undefined ? 0 : carbohydratesPercent }  sx={{
                        backgroundColor: '#e0e0e0',
                        '& .MuiLinearProgress-bar': {
                            backgroundColor: carbohydratesBoxColor,
                        },
                    }}/>
                </Box>
                <p>{metabolicRatesTodayData?.carbohydratesCurrent ?? 0} / {metabolicRatesTodayData?.carbohydratesMax ?? 0} g </p>

            </div>

            <div className={"col-4 p-3 "}>
                <p className={"mb-0 pt-0"}>Protein</p>
                <Box sx={{ width: '100%', fill: "#f68247" }}>
                    <LinearProgress variant="determinate" value={ props.appUser === undefined ? 0 : proteinPercent }  sx={{
                        backgroundColor: '#e0e0e0',
                        '& .MuiLinearProgress-bar': {
                            backgroundColor: proteinBoxColor,
                        },
                    }}/>
                </Box>
                <p>{metabolicRatesTodayData?.proteinCurrent ?? 0} / {metabolicRatesTodayData?.proteinMax ?? 0} g</p>
            </div>

            <div className={"col-12"}>
                <ListItemButton component="a"  sx={{color: '#f68247', textAlign: 'center'}} onClick={ () =>
                    navigate("/meal/overview")
                }>
                    <ListItemText primary="Details anzeigen"  />
                </ListItemButton>
            </div>

        </div>

    </div>
    )
}