package com.parkingspot.parkingspot.controllers;

import com.parkingspot.parkingspot.dtos.ParkingSpotDTO;
import com.parkingspot.parkingspot.models.ParkingSpotModel;
import com.parkingspot.parkingspot.models.Status;
import com.parkingspot.parkingspot.services.ParkingSpotService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Controller
@RequestMapping(value = "/parking-spot")
public class ParkingSpotInterfaceController {

    final ParkingSpotService parkingSpotService;
    final String prefixTemplate = "parking-spot/";

    public ParkingSpotInterfaceController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @GetMapping("/")
    public String listar(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            Model model){

        Sort sort = Sort.by(Sort.Direction.ASC, "parkingNumber");
        Pageable paginacao = PageRequest.of(pageNumber, 5, sort);

        Page<ParkingSpotModel> listaParkingSpot = parkingSpotService.findAll(paginacao);
        model.addAttribute("parkingSpotsList", listaParkingSpot);
        return prefixTemplate + "listar";
    }

    public ModelAndView listar(Status status){
        Sort sort = Sort.by(Sort.Direction.ASC, "parkingNumber");
        Pageable paginacao = PageRequest.of(0, 5, sort);

        Page<ParkingSpotModel> listaParkingSpot = parkingSpotService.findAll(paginacao);
        ModelAndView mv = new ModelAndView(this.prefixTemplate + "listar");

        mv.addObject("parkingSpotsList", listaParkingSpot);
        mv.addObject("statusTitulo",status.getTitulo());
        mv.addObject("statusMensagem", status.getMensagem());
        mv.addObject("statusClasse",status.getClasse());

        return mv;
    }

    @GetMapping("/ver/{id}")
    public ModelAndView visualizar(@PathVariable(value = "id") Long id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return this.listar(new Status("Visualizar item:","Item não encontrado!","alert-danger"));
        }

        return new ModelAndView(this.prefixTemplate+"ver")
                .addObject("parkingSpot",parkingSpotModelOptional.get());
    }

    @GetMapping("/cadastrar")
    public String cadastrar(ParkingSpotDTO parkingSpotDTO){

//        Random rand = new Random();
//        parkingSpotDTO.setOwner("Tiago " + rand.nextInt());
//        parkingSpotDTO.setParkingNumber(""+rand.nextInt(10000));
//        parkingSpotDTO.setCarNumber(""+rand.nextInt(10000));
//        parkingSpotDTO.setLocalization("Andar "+rand.nextInt());

        return "parking-spot/cadastrar";
    }

    @PostMapping("/salvarnovo")
    public ModelAndView salvar(@Valid ParkingSpotDTO parkingSpotDTO, BindingResult result, Model model){
        if(!result.hasErrors()){
            ParkingSpotModel parkingSpotModel = new ParkingSpotModel();
            BeanUtils.copyProperties(parkingSpotDTO, parkingSpotModel);
            parkingSpotModel.setDate(LocalDateTime.now(ZoneId.of("UTC")));
            parkingSpotService.save(parkingSpotModel);

            model.addAttribute("parkingSpot", parkingSpotModel);
            model.addAttribute("success","true");

            return new ModelAndView(this.prefixTemplate + "ver")
                    .addObject("parkingSpot", parkingSpotModel)
                    .addObject("success","true");
        }else{
            return new ModelAndView(this.prefixTemplate + "cadastrar");
        }
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable(value = "id") Long id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return this.listar(new Status("Editar item:","Item não encontrado!","alert-danger"));
        }

        return new ModelAndView(prefixTemplate + "editar").
                addObject("parkingSpot",parkingSpotModelOptional.get());
    }

    @PostMapping("/atualizar/{id}")
    public ModelAndView atualizar(@PathVariable("id") Long id, @ModelAttribute("parkingSpot") @Valid ParkingSpotDTO parkingSpotDTO, BindingResult result, Model model){
        if(result.hasErrors()){
            return new ModelAndView(this.prefixTemplate + "editar");
        }else{
            ParkingSpotModel parkingSpotModel = new ParkingSpotModel();
            BeanUtils.copyProperties(parkingSpotDTO, parkingSpotModel);
            parkingSpotModel.setId(id);
            parkingSpotModel.setDate(LocalDateTime.now(ZoneId.of("UTC")));
            parkingSpotService.save(parkingSpotModel);
            return this.listar(new Status("Edição de item:","Item editado com sucesso!","alert-success"));
        }
    }

    @GetMapping("/excluir/{id}")
    public ModelAndView excluir(@PathVariable("id") Long id, Model model){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return this.listar(new Status("Excluir item:","Item não encontrado!","alert-danger"));
        }

        return new ModelAndView(this.prefixTemplate + "excluir")
                .addObject("parkingSpot",parkingSpotModelOptional.get());
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deletar(@PathVariable("id") Long id) {
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return this.listar(new Status("Excluir item:","Item não encontrado!","alert-danger"));
        }
        parkingSpotService.delete(parkingSpotModelOptional.get());
        return this.listar(new Status("Excluir item:","Item excluído com sucesso!","alert-success"));
    }
}
