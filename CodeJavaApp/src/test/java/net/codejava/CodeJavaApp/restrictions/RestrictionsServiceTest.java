package net.codejava.CodeJavaApp.restrictions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.codejava.CodeJavaApp.restrictions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
public class RestrictionsServiceTest {
    @Mock
    private RestrictionsRepository restrictions;

    @InjectMocks
    private RestrictionsServiceImpl restrictionsService;

    @Test
    public void getRestriction_Success() {
        //arrange
        Restrictions restriction = new Restrictions(1L, "Indoor", "Category", "2 pax");
        //mock
        when(restrictions.save(any(Restrictions.class))).thenReturn(restriction);
        when(restrictions.findById(anyLong())).thenReturn(Optional.of(restriction));

        //act
        Restrictions savedRestriction = restrictionsService.addRestrictions(restriction);
        Restrictions getRestriction = restrictionsService.getRestriction(restriction.getId());
//        restrictions.findById(restriction.getId());

        //assert
        assertNotNull(getRestriction);

        //verify
        verify(restrictions).save(restriction);
    }

    @Test
    public void addRestriction_NewDescription_ReturnSavedRestriction(){
        // arrange ***
        Restrictions restriction = new Restrictions(1L, "Indoor", "Category", "Only 5 pax allowed for social gatherings");

        // mock the "save" operation
        when(restrictions.save(any(Restrictions.class))).thenReturn(restriction);

        // act ***
        Restrictions savedRestriction = restrictionsService.addRestrictions(restriction);

        // assert ***
        assertNotNull(savedRestriction);

        //verify
        verify(restrictions).save(restriction);
    }

    @Test
    public void updateRestriction_ValidRestrictionId_ReturnUpdatedRestriction(){
        //arrange
        Restrictions restriction = new Restrictions(1L,"Indoor", "Category", "Only 5 pax allowed for social gatherings");
        //mock
        when(restrictions.save(any(Restrictions.class))).thenReturn(restriction);
        when(restrictions.findById(anyLong())).thenReturn(Optional.of(restriction));
        //act
        Restrictions savedRestriction = restrictionsService.addRestrictions(restriction);
        Restrictions savedNewRestriction = restrictionsService.updateRestrictions(savedRestriction.getId(), restriction);
        //assert
        assertNotNull(savedNewRestriction);
        //verify
        verify(restrictions, times(2)).save(restriction);
        verify(restrictions).findById(savedRestriction.getId());
    }

    //Catch RestrictionNotFoundException
    @Test
    public void updateRestriction_InvalidRestrictionId_ReturnNull(){
        //Arrange
        Restrictions newRestriction = new Restrictions(1L, "Indoor", "Category", "newDesciption");

        //mock
        when(restrictions.findById(anyLong())).thenReturn(Optional.empty());

        //act
        Restrictions updatedRestriction = restrictionsService.updateRestrictions(10L, newRestriction);
        //assert
        assertNull(updatedRestriction);

        //verify
        verify(restrictions).findById(10L);
    }

}