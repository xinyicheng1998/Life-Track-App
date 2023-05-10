import React, { useState, useEffect } from 'react'
import axios from 'axios'
import { format } from 'date-fns'
import {
  BarChart,
  XAxis,
  YAxis,
  Tooltip,
  Legend,
  CartesianGrid,
  Bar,
  ResponsiveContainer,
} from 'recharts'

const Activity = () => {
  const [userId, setUserId] = useState('')
  const [startDate, setStartDate] = useState(
    new Date().toISOString().slice(0, 10)
  )
  const [timeRange, setTimeRange] = useState(6)
  const [activities, setActivities] = useState([])
  const [mostSteps, setMostSteps] = useState(null)

  useEffect(() => {
    fetchData()
  }, [])

  const fetchData = async () => {
    try {
      const formattedStartDate = format(new Date(startDate), 'yyyyMMdd')

      const allStepsResponse = await axios.get(`/all-steps`, {
        params: {
          personId: userId,
          date: formattedStartDate,
          daysAfter: timeRange,
        },
      })
      const mostStepsResponse = await axios.get(`/most-steps`, {
        params: {
          personId: userId,
          date: formattedStartDate,
          daysAfter: timeRange,
        },
      })

      setActivities(allStepsResponse.data.result)

      setMostSteps(mostStepsResponse.data)
    } catch (error) {
      console.error('Error fetching data:', error)
    }
  }

  const onSubmit = (e) => {
    e.preventDefault()
    if (timeRange < 0 || timeRange > 13) {
      alert('Please enter a time range between 0 and 13, inclusive.')
      return
    }
    fetchData()
  }

  const chartData = activities.map((activity) => ({
    date: activity.date,
    calories: activity.calories,
  }))

  console.log(chartData)
  return (
    <div>
      <h2>Activity</h2>
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
        <h3>Calories Burned Chart</h3>
        <BarChart
          width={600}
          height={300}
          data={chartData}
          margin={{
            top: 5,
            right: 30,
            left: 20,
            bottom: 5,
          }}
        >
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="date" />
          <YAxis />
          <Tooltip />
          <Legend />
          <Bar dataKey="calories" fill="#8884d8" />
        </BarChart>
      </div>

      {mostSteps && (
        <div>
          <h3>Most Steps Date</h3>
          <p>Date: {mostSteps.date}</p>
          <p>Total Steps: {mostSteps.totalSteps}</p>
          <p>Calories: {mostSteps.calories}</p>
          <table>
            <thead>
              <tr>
                <th>Activity Type</th>
                <th>Calories</th>
              </tr>
            </thead>
            <tbody>
              {mostSteps.summaryActivities.map((activity, index) => (
                <tr key={index}>
                  <td>{activity.activityType}</td>
                  <td>{activity.calories}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  )
}

export default Activity
