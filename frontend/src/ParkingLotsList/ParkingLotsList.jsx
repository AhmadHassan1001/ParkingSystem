import React from 'react'
import ParkingLotGrid from './components/ParkingLotGrid/ParkingLotGrid'
import Filters from './components/Filters/Filters'
import styles from './ParkingLotsList.module.css'

function ParkingLotsList() {
  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <aside className={styles.sidebar}>
          <Filters />
        </aside>
        <main className={styles.main}>
          <ParkingLotGrid />
        </main>
      </div>
    </div>
  )
}

export default ParkingLotsList