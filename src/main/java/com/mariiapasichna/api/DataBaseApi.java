package com.mariiapasichna.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mariiapasichna.dao.ImageDao;
import com.mariiapasichna.model.Image;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.List;

@Path("/database")
public class DataBaseApi {
    private ImageDao imageDao = new ImageDao();

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createImage(@FormParam("name") String name, @FormParam("image") String image) {
        Image inputImage = new Image(name, image);
        Image saveImage = imageDao.addImage(inputImage);
        if (saveImage.getId() != 0) {
            String resultJson = "{\"result\":\"success\"}";
            return Response.status(Response.Status.OK).entity(resultJson).build();
        } else {
            String resultJson = "{\"result\":\"failed\"}";
            return Response.status(Response.Status.BAD_REQUEST).entity(resultJson).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getImage(@PathParam("id") int id) {
        List<Image> images = imageDao.getImage(id);
        if (images.size() != 0) {
            byte[] arr = Base64.getDecoder().decode(images.get(0).getImage());
            return Response.status(Response.Status.OK).entity(new ByteArrayInputStream(arr)).build();
        } else {
            String result = "{\"result\":\"image not found\"}";
            return Response.status(Response.Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getImagesList() {
        List<Image> images = imageDao.getImages();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        String resultJson = gson.toJson(images);
        return Response.status(Response.Status.OK).entity(resultJson).build();
    }
}