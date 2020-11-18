import React, { useState } from 'react';
import { TextField, Grid, Button } from '@material-ui/core';
import axios from 'axios';
import ResultTable from './Table';

import logo from './duco.png';
import './App.css';

const App = () => {
  const [leftSide, setLeftSide] = useState('');
  const [rightSide, setRightSide] = useState('');
  const [rows, setRows] = useState([]);
  const [dynamicColSize, setDynamicColSize] = useState(0);
  const [matchTime, setMatchTime] = useState(0);
  const [loading, setLoading] = useState(false);
  const handleClick = () => {
    setLoading(true);
    const leftFields = leftSide.trim().split('\n').map((row, i) => row.split(',').map(c => c.trim()));
    const rightFields = rightSide.trim().split('\n').map((row, i) => row.split(',').map(c => c.trim()));
    if (leftFields.length || rightFields.length) {
      axios.post('http://localhost:8000/api/matching',
        { leftSide: leftFields, rightSide: rightFields },
        { headers: { 'Content-Type': 'application/json' } },
      )
      .then((res) => {
        setRows([...res.data.matches, ...res.data.unmatched]);
        setDynamicColSize(Math.max(res.data.matches?.[0]?.left?.fields?.length || 0,
          res.data.unmatched?.[0]?.fields?.length || 0));
        setMatchTime(res.data.time);
      })
      .then(() => setLoading(false))
      .catch(() => {
        alert('failed');
      });
    }
  };
  const handleLeftChange = e => setLeftSide(e.target.value);
  const handleRightChange = e => setRightSide(e.target.value);
  return (
    <div className="App">
      <Grid container spacing={3}>
        <Grid item xs={12}>
          <img src={logo} alt="Duco Logo" />
        </Grid>
        <Grid item xs={12}/>
        <Grid item xs={12} sm={1}/>
        <Grid item xs={12} sm={4}>
          <TextField
            id="outlined-textarea"
            label="Left"
            placeholder="Input your data for the left side"
            multiline
            variant="outlined"
            fullWidth
            value={leftSide}
            onChange={handleLeftChange}
            rows={5}
          />
        </Grid>
        <Grid item xs={12} sm={2}>
          <Button
            variant="contained"
            color="secondary"
            onClick={handleClick}
            disabled={!leftSide || !rightSide || loading}
          >
            Match
          </Button>
          {!!matchTime && (
            <span className="match-time">Match time: {(matchTime.nano / (1000 * 1000 * 1000)) + matchTime.seconds}s</span>
          )}
        </Grid>
        <Grid item xs={12} sm={4}>
          <TextField
            id="outlined-textarea"
            label="Right"
            placeholder="Input your data for the right side"
            multiline
            variant="outlined"
            fullWidth
            value={rightSide}
            onChange={handleRightChange}
            rows={5}
          />
        </Grid>
        <Grid item xs={12} sm={1}/>
        <Grid item xs={12} sm={2}/>
        <Grid item xs={12} sm={8}>
          <h2>Result</h2>
          <ResultTable rows={rows} dynamicColSize={dynamicColSize} loading={loading}/>
        </Grid>
      </Grid>
    </div>
  );
};

export default App;
