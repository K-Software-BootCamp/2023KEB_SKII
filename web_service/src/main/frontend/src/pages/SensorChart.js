import React from 'react';
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend,
} from 'chart.js';
import { Line } from 'react-chartjs-2';
import zoomPlugin from 'chartjs-plugin-zoom';

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend,
    zoomPlugin
);

function SensorChart({ datasetLabel, datasetData_v, datasetData_h }) {
    const options = {
        maintainAspectRatio: false,
        responsive: true,
        elements: {
            point: {
                radius: 0,
            },
        },
        plugins: {
            legend: {
                position: 'top',
            },
            zoom: {
                zoom: {
                    wheel: {
                        enabled: true,
                    },
                    pinch: {
                        enabled: true
                    },
                    mode: 'xy',
                }
            }
        }
    };

    const data = {
        labels: datasetLabel,
        datasets: [
            {
                label: "Vertical",
                data: datasetData_v,
                borderColor: '#8A96A8',
            },
            {
                label: "Horizontal",
                data: datasetData_h,
                borderColor: '#697077',
            }
        ],
    };

    return <Line options={options} data={data}/>;
}

export default SensorChart;
