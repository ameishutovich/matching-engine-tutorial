import React from 'react';
import { makeStyles } from '@material-ui/core';
import { DataGrid } from '@material-ui/data-grid';

const useStyles = makeStyles({
  container: {
    height: 400,
    width: '100%',
    '& .match-cell': {
      lineHeight: '25px !important',
    },
  },
});

const ResultTable = (props) => {
  const { rows, dynamicColSize, loading } = props;
  const modifiedRows = rows
    .filter(row => (row.left && row.right) || row.id)
    .map(row => {
      if (row.left && row.right) {
        return {
          id: `LEFT${row.left.id}-RIGHT${row.right.id}`,
          matchStatus: 'Match',
          side: ['LEFT', 'RIGHT'],
          ...(Array(dynamicColSize).fill().reduce((acc, _, i) => ({
            ...acc,
            [`column${i + 1}`]: [row.left.fields[i], row.right.fields[i]],
          }), {})),
        }
      }
      return {
        id: `${row.side}${row.id}`,
        matchStatus: 'Unmatched',
        side: [row.side],
        ...(Array(dynamicColSize).fill().reduce((acc, _, i) => ({
          ...acc,
          [`column${i + 1}`]: [row.fields[i]],
        }), {})),
      };
    });
  const columns = [
    {
      field: 'id',
      headerName: 'Id',
      cellClassName: params => params.value.split('-')[1] ? 'match-cell' : 'common-cell',
      renderCell: params => params.value.split('-')[1] ? (
        <>
          {params.value.split('-')[0]}
            <br></br>
          {params.value.split('-')[1]}
        </>
      ) : (
        <>{params.value}</>
      ),
    },
    {
      field: 'matchStatus',
      headerName: 'Match Status',
      cellClassName: 'common-cell',
      width: 150,
    },
    {
      field: 'side',
      headerName: 'Side',
      cellClassName: params => params.value.length === 2 ? 'match-cell' : 'common-cell',
      sortable: false,
      renderCell: params => params.value.length === 2 ? (
        <>
          {params.value[0]}
            <br></br>
          {params.value[1]}
        </>
      ) : (
        <>{params.value[0]}</>
      ),
    },
    ...(Array(dynamicColSize).fill().map((_, i) => ({
      field: `column${i + 1}`,
      headerName: `Column ${i + 1}`,
      cellClassName: params => params.value.length === 2 ? 'match-cell' : 'common-cell',
      sortable: false,
      renderCell: params => params.value.length === 2 ? (
        <>
          {params.value[0]}
            <br></br>
          {params.value[1]}
        </>
      ) : (
        <>{params.value[0]}</>
      ),
    }))),
  ];
  const sortModel = [
    {
      field: 'id',
      sort: 'asc',
    },
    {
      field: 'matchStatus',
      sort: 'asc',
    },
  ];
  const classes = useStyles();

  return (
    <div className={classes.container}>
      <DataGrid
        rows={modifiedRows}
        columns={columns}
        sortModel={sortModel}
        loading={loading}
        pageSize={100}
        rowsPerPageOptions={[10, 20, 100, 200, 500, 1000]}
      />
    </div>
  );
};

export default ResultTable;
