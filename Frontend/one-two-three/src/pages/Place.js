import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { format } from 'date-fns';
import {
  BarChart,
  XAxis,
  YAxis,
  Tooltip,
  Legend,
  CartesianGrid,
  Bar,
} from 'recharts';

const Place = () => {
  const [userId, setUserId] = useState('');
  const [startDate, setStartDate] = useState(
    new Date().toISOString().slice(0, 10)
  );
  const [timeRange, setTimeRange] = useState(6);
  const [mostVisited, setMostVisited] = useState(null);
  const [places, setPlaces] = useState({});

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const formattedStartDate = format(new Date(startDate), 'yyyyMMdd');

      const mostVisitedResponse = await axios.get(`/most-visited-places`, {
        params: {
          personId: userId,
          date: formattedStartDate,
          daysAfter: timeRange,
        },
      });

      setMostVisited(mostVisitedResponse.data);
      setPlaces(mostVisitedResponse.data.visitedPlaces);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

  const onSubmit = (e) => {
    e.preventDefault();
    if (timeRange < 0 || timeRange > 13) {
      alert('Please enter a time range between 0 and 13, inclusive.');
      return;
    }
    fetchData();
  };

  const chartData = [];
  for (const [key, value] of Object.entries(places)) {
    const pair = { place: key, times: value };
    chartData.push(pair);
  }
  console.log(chartData);

  return (
    <div>
      <h2>Visited Times for Places</h2>
      <form onSubmit={onSubmit}>
        <label>
          User ID:
          <input
            type="text"
            value={userId}
            onChange={(e) => setUserId(e.target.value)}
          />
        </label>
        <label>
          Start Date:
          <input
            type="date"
            value={startDate}
            onChange={(e) => setStartDate(e.target.value)}
          />
        </label>
        <label>
          Time Range:
          <input
            type="number"
            value={timeRange}
            onChange={(e) => setTimeRange(parseInt(e.target.value))}
          />
        </label>
        <button type="submit">Submit</button>
      </form>
      <div>
        <h3>Visited Times Chart</h3>
        <BarChart
          width={1500}
          height={500}
          data={chartData}
          margin={{
            top: 5,
            right: 30,
            left: 20,
            bottom: 80,
          }}
        >
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis
            dataKey="place"
            interval={0}
            angle={-45}
            textAnchor="end"
            tick={{ fontSize: 10 }}
          />
          <YAxis />
          <Tooltip />
          <Legend />
          <Bar dataKey="times" fill="#8884d8" />
        </BarChart>
      </div>
      {mostVisited && (
        <div>
          <h3>Most Visited Place</h3>
          <p>Place: {mostVisited.mostVisitedPlace}</p>
        </div>
      )}
    </div>
  );
};

export default Place;
