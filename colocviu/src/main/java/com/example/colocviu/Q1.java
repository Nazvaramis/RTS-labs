package com.example.colocviu;
import java.util.ArrayList;
import Components.Activation;
import Components.Condition;
import Components.GuardMapping;
import Components.PetriNet;
import Components.PetriNetWindow;
import Components.PetriTransition;
import DataObjects.DataFloat;
import DataObjects.DataSubPetriNet;
import DataObjects.DataTransfer;
import DataOnly.SubPetri;
import DataOnly.TransferOperation;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;





    public class Q1 {


        public static void main(String[] args) {

            // main ------------------------------------------
            PetriNet pn = new PetriNet();
            pn.PetriNetName = "PN1";
            pn.NetworkPort = 1080;

            // subpetri net --------------------------------------
            PetriNet spn = new PetriNet();
            spn.PetriNetName = "PN3";

            DataFloat subConstantValue1 = new DataFloat();
            subConstantValue1.SetName("subConstantValue1");
            subConstantValue1.SetValue(0.01f);
            spn.ConstantPlaceList.add(subConstantValue1);

            DataFloat p8 = new DataFloat();
            p8.SetName("p8");
            spn.PlaceList.add(p8);

            DataFloat p9 = new DataFloat();
            p9.SetName("p9");
            spn.PlaceList.add(p9);

            DataFloat p10 = new DataFloat();
            p10.SetName("p10");
            spn.PlaceList.add(p10);

            DataTransfer p11 = new DataTransfer();
            p11.SetName("p11");
            p11.Value = new TransferOperation("localhost", "1081", "p7");
            spn.PlaceList.add(p11);

            // Transition T6 --------------------------------------
            PetriTransition t6 = new PetriTransition(spn);
            t6.TransitionName = "t6";
            t6.InputPlaceName.add("p8");

            Condition T6Ct1 = new Condition(t6, "p8", TransitionCondition.NotNull);

            GuardMapping grdT6 = new GuardMapping();
            grdT6.condition = T6Ct1;

            ArrayList<String> lstInput = new ArrayList<String>();
            lstInput.add("p8");
            lstInput.add("subConstantValue1");
            grdT6.Activations.add(new Activation(t6, lstInput, TransitionOperation.Prod, "p9"));

            t6.GuardMappingList.add(grdT6);
            t6.Delay = 0;
            spn.Transitions.add(t6);

            // Transition T7 --------------------------------------

            PetriTransition t7 = new PetriTransition(spn);
            t7.TransitionName = "t7";
            t7.InputPlaceName.add("p9");

            Condition T7Ct1 = new Condition(t7, "p9", TransitionCondition.NotNull);

            GuardMapping grdT7 = new GuardMapping();
            grdT7.condition = T7Ct1;

            //grdT7.Activations.add(new Activation(t7,"p9", TransitionOperation.SendOverNetwork, "p11"));
            //grdT7.Activations.add(new Activation(t7,"p9", TransitionOperation.Move, "p10"));
            //grdT7.Activations.add(new Activation(t7,"p9", TransitionOperation.Move, "p11"));


            t7.GuardMappingList.add(grdT7);
            t7.Delay = 0;
            spn.Transitions.add(t7);

            // Transition T8 --------------------------------------
            PetriTransition t8 = new PetriTransition(spn);
            t8.TransitionName = "t8";
            t8.InputPlaceName.add("p10");

            Condition T8Ct1 = new Condition(t8, "p10", TransitionCondition.NotNull);

            GuardMapping grdT8 = new GuardMapping();
            grdT8.condition = T8Ct1;
            grdT8.Activations.add(new Activation(t8,"", TransitionOperation.StopPetriNet, ""));

            t8.GuardMappingList.add(grdT8);
            t8.Delay = 0;
            spn.Transitions.add(t8);

            spn.Delay = 3000;

            // ----------------------------------------------------
            DataSubPetriNet subPetriNet = new DataSubPetriNet();
            subPetriNet.SetName("SubPetri2");
            SubPetri sptr= new SubPetri(spn);
            subPetriNet.SetValue(sptr);
            pn.ConstantPlaceList.add(subPetriNet);

            DataFloat p0 = new DataFloat();
            p0.SetName("p0");
            p0.SetValue(1.0f);
            pn.PlaceList.add(p0);

            DataSubPetriNet p1 = new DataSubPetriNet();
            p1.SetName("p1");
            pn.PlaceList.add(p1);

            DataFloat p2 = new DataFloat();
            p2.SetName("p2");
            pn.PlaceList.add(p2);

            DataFloat p3 = new DataFloat();
            p3.SetName("p3");
            pn.PlaceList.add(p3); // input

            // Transition T0 --------------------------------------
            PetriTransition t0 = new PetriTransition(pn);
            t0.TransitionName = "t0";
            t0.InputPlaceName.add("p0");
            t0.InputPlaceName.add("p3");

            Condition T0Ct1 = new Condition(t0, "p0", TransitionCondition.NotNull);
            Condition T0Ct2 = new Condition(t0, "p3", TransitionCondition.NotNull);
            T0Ct1.SetNextCondition(LogicConnector.AND, T0Ct2);

            GuardMapping grdT0 = new GuardMapping();
            grdT0.condition = T0Ct1;
            grdT0.Activations.add(new Activation(t0, "SubPetri2", TransitionOperation.Move, "p1"));
            grdT0.Activations.add(new Activation(t0, "p3", TransitionOperation.Move, "p1-p8"));
            grdT0.Activations.add(new Activation(t0, "p1", TransitionOperation.ActivateSubPetri, ""));

            t0.GuardMappingList.add(grdT0);
            t0.Delay = 0;
            pn.Transitions.add(t0);

            // Transition T1 ------------------------------------------

            PetriTransition t1 = new PetriTransition(pn);
            t1.TransitionName = "t1";
            t1.InputPlaceName.add("p1");

            Condition T1Ct1 = new Condition(t1, "p1", TransitionCondition.NotNull);
            Condition T1Ct2 = new Condition(t1, "p1", TransitionCondition.SubPetriStopped);
            T1Ct1.SetNextCondition(LogicConnector.AND, T1Ct2);

            GuardMapping grdT1 = new GuardMapping();
            grdT1.condition = T1Ct1;
            grdT1.Activations.add(new Activation(t1, "p1-p11", TransitionOperation.Copy, "p2"));

            t1.GuardMappingList.add(grdT1);
            t1.Delay = 0;
            pn.Transitions.add(t1);

            // Transition T2 ------------------------------------------

            PetriTransition t2 = new PetriTransition(pn);
            t2.TransitionName = "t2";
            t2.InputPlaceName.add("p2");

            Condition T2Ct1 = new Condition(t2, "p2", TransitionCondition.NotNull);

            GuardMapping grdT2 = new GuardMapping();
            grdT2.condition = T2Ct1;
            grdT2.Activations.add(new Activation(t2, "p2", TransitionOperation.Move, "p0"));

            t2.GuardMappingList.add(grdT2);
            t2.Delay = 0;
            pn.Transitions.add(t2);

            System.out.println("Colloquium started \n ------------------------------");
            pn.Delay = 3000;

            PetriNetWindow frame = new PetriNetWindow(false);
            frame.petriNet = pn;
            frame.setVisible(true);

            // PN2 -------------------------------------------------
            PetriNet pn2 = new PetriNet();
            pn2.PetriNetName = "PN2";
            pn2.NetworkPort = 1081;

            DataFloat p4 = new DataFloat();
            p4.SetName("p4");
            p4.SetValue(1.0f);
            pn2.PlaceList.add(p4);

            DataFloat p5 = new DataFloat();
            p5.SetName("p5");
            pn2.PlaceList.add(p5);

            DataFloat p6 = new DataFloat();
            p6.SetName("p6");
            pn2.PlaceList.add(p6);

            DataFloat p7 = new DataFloat();
            p7.SetName("p7");
            pn2.PlaceList.add(p7);

            // Transition T3 ------------------------------------------

            PetriTransition t3 = new PetriTransition(pn2);
            t3.TransitionName = "t3";
            t3.InputPlaceName.add("p4");
            t3.InputPlaceName.add("p11");

            Condition T3Ct1 = new Condition(t3, "p4", TransitionCondition.NotNull);
            Condition T3Ct2 = new Condition(t3, "p7", TransitionCondition.NotNull);

            T3Ct1.SetNextCondition(LogicConnector.AND, T3Ct2);

            GuardMapping grdT3 = new GuardMapping();
            grdT3.condition = T3Ct1;
            grdT3.Activations.add(new Activation(t3, "p4", TransitionOperation.Move, "p5"));

            t3.GuardMappingList.add(grdT3);
            t3.Delay = 0;
            pn2.Transitions.add(t3);

            // Transition T4 ------------------------------------------

            PetriTransition t4 = new PetriTransition(pn2);
            t4.TransitionName = "t4";
            t4.InputPlaceName.add("p5");

            Condition T4Ct1 = new Condition(t4, "p5", TransitionCondition.NotNull);

            GuardMapping grdT4 = new GuardMapping();
            grdT4.condition = T4Ct1;
            grdT4.Activations.add(new Activation(t4, "p5", TransitionOperation.Move, "p6"));

            t4.GuardMappingList.add(grdT4);
            t4.Delay = 0;
            pn2.Transitions.add(t4);

            // Transition T5 ---------------------------------------------

            PetriTransition t5 = new PetriTransition(pn2);
            t5.TransitionName = "t5";
            t5.InputPlaceName.add("p6");

            Condition T5Ct1 = new Condition(t5, "p6", TransitionCondition.NotNull);

            GuardMapping grdT5 = new GuardMapping();
            grdT5.condition = T5Ct1;
            grdT5.Activations.add(new Activation(t2, "p6", TransitionOperation.Move, "p4"));

            t5.GuardMappingList.add(grdT5);
            t5.Delay = 0;
            pn2.Transitions.add(t5);


            PetriNetWindow frame2 = new PetriNetWindow(false);
            frame2.petriNet = pn2;
            frame2.setVisible(true);


        }
    }


