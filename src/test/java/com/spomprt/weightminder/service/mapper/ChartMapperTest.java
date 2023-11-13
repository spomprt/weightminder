package com.spomprt.weightminder.service.mapper;

import com.spomprt.weightminder.domain.Person;
import com.spomprt.weightminder.domain.Record;
import com.spomprt.weightminder.external.model.Chart;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class ChartMapperTest {

    @Test
    void toChart() {
        //arrange
        Person person = Person.newPerson(1L, 1L);

        LocalDate now = LocalDate.now();
        Record r1 = Record.newRecord(person, 101.0);
        ReflectionTestUtils.setField(r1, "createdAt", now);
        Record r2 = Record.newRecord(person, 99.0);
        ReflectionTestUtils.setField(r2, "createdAt", now.minusDays(1));
        Record r3 = Record.newRecord(person, 86.0);
        ReflectionTestUtils.setField(r3, "createdAt", now.minusDays(2));

        List<Record> records = List.of(r1, r2, r3);

        ReflectionTestUtils.setField(person, "records", records);

        //act
        Chart chart = ChartMapper.map(person.getLastTen());

        //assert
        assertThat(chart.type()).isEqualTo("line");
        assertThat(chart.data().labels()).hasSize(3);
        assertThat(chart.data().datasets().get(0).data()).hasSize(3);
    }

}