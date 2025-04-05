import {Box, LinearProgress} from "@mui/material";
import {Gauge, gaugeClasses} from "@mui/x-charts";
import {AppUser} from "../model/AppUser.ts";
import HomeWelcomeText from "./elements/HomeWelcomeText.tsx";
import HomeAuthText from "./elements/HomeAuthText.tsx";
import {useEffect, useState} from "react";

type Props = {
    appUser: AppUser | undefined | null
}

export default function HomeSite(props: Readonly<Props>) {

    const [greeting, setGreeting] =  useState("Hallo");

    useEffect(() => {
        const hour = new Date().getHours();
        if(hour >= 5 && hour < 11) setGreeting("Guten Morgen");
        else if(hour >= 11 && hour < 18) setGreeting("Guten Tag");
        else setGreeting("Guten Abend");
    }, [])

    return (
        <div className={"row "}>

            <div className={"col-md-12 col-lg-6 text-start p-5"}>

                <h3 className={"mb-4"}>{greeting}, {props.appUser?.firstname ?? "Unbekannt"},</h3>

                { props.appUser === undefined ? <HomeAuthText /> : <HomeWelcomeText/> }

            </div>
            <div className={"col-md-12 col-lg-6 p-4 mt-3"}>
                <div className={"col-12 box1  pt-2"}>

                    <div className={"row mb-3"}>
                        <div className={"col-12"}>
                            <h2 className={"fw-bold"}>Heute</h2>
                        </div>
                    </div>

                    <div className={"row"}>
                        <Gauge
                            height={200}
                            width={400}
                            value={ props.appUser === undefined ? 0 : 1950 }
                            valueMax={2564}
                            startAngle={-110}
                            endAngle={110}
                            cornerRadius="50%"
                            sx={{
                                [`& .${gaugeClasses.valueText}`]: {
                                    fontSize: 30,
                                    transform: 'translate(0px, 0px)',
                                },
                                [`& .${gaugeClasses.valueArc}`]: {
                                    fill: '#f68247',
                                },
                            }}
                            text={
                                ({ value, valueMax }) => `${value} / ${valueMax}`
                            }
                        />
                    </div>

                    <div className={"row p-5"}>
                        <div className={"col-4 p-3 "}>
                            <Box sx={{ width: '100%', fill: "#f68247" }}>
                                {/* Setze einen statischen Wert für den Fortschritt */}
                                <LinearProgress variant="determinate" value={ props.appUser === undefined ? 0 : 50  }  sx={{
                                    backgroundColor: '#e0e0e0', // Hintergrundfarbe (Track)
                                    '& .MuiLinearProgress-bar': {
                                        backgroundColor: '#799a61', // Fortschrittsfarbe (Bar)
                                    },
                                }}/>
                            </Box>
                            <p>Fett</p>

                        </div>

                        <div className={"col-4 p-3 "}>
                            <Box sx={{ width: '100%', fill: "#f68247" }}>
                                {/* Setze einen statischen Wert für den Fortschritt */}
                                <LinearProgress variant="determinate" value={ props.appUser === undefined ? 0 : 70}  sx={{
                                    backgroundColor: '#e0e0e0', // Hintergrundfarbe (Track)
                                    '& .MuiLinearProgress-bar': {
                                        backgroundColor: '#799a61', // Fortschrittsfarbe (Bar)
                                    },
                                }}/>
                            </Box>
                            <p>KH</p>

                        </div>

                        <div className={"col-4 p-3 "}>
                            <Box sx={{ width: '100%', fill: "#f68247" }}>
                                {/* Setze einen statischen Wert für den Fortschritt */}
                                <LinearProgress variant="determinate" value={ props.appUser === undefined ? 0 : 30 }  sx={{
                                    backgroundColor: '#e0e0e0', // Hintergrundfarbe (Track)
                                    '& .MuiLinearProgress-bar': {
                                        backgroundColor: '#799a61', // Fortschrittsfarbe (Bar)
                                    },
                                }}/>
                            </Box>
                            <p>Protein</p>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    )
}