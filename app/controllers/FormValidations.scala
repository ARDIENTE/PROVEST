package controllers

import java.time.Instant
import java.util.UUID
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._
import models.domain._

object FormValidations {
  def addLocAndVacinityForm = Form(tuple(
      "project_id"      ->  nonEmptyText,
      "sub_project_id"  ->  nonEmptyText,
      "description"     ->  text,
      "created_at"      ->  ignored(Instant.now)))
  def addAmenitiesAndFacilityForm = Form(tuple(
      "project_id"      ->  nonEmptyText,
      "sub_project_id"  ->  nonEmptyText,
      "title"           ->  nonEmptyText,
      "description"     ->  text,
      "created_at"      ->  ignored(Instant.now)))
  def addConstructionUpdateForm = Form(tuple(
      "project_id"      ->  nonEmptyText,
      "sub_project_id"  ->  nonEmptyText,
      "title"     ->  nonEmptyText,
      "created_at"      ->  ignored(Instant.now)))
  def addContactProjectForm = Form(tuple(
      "project_id"      ->  nonEmptyText,
      "sub_project_id"  ->  nonEmptyText,
      "name"            ->  nonEmptyText,
      "position"        ->  nonEmptyText,
      "number"          ->  nonEmptyText,
      "created_at"      ->  ignored(Instant.now)))
  def addEmailForm = Form(mapping(
      "id"              ->  ignored(UUID.randomUUID),
      "title"           ->  nonEmptyText,
      "mail"            ->  email,
      "created_at"      ->  ignored(Instant.now)
    )(Email.apply)(Email.unapply))
  def addOverViewForm = Form(tuple(
      "project_id"      ->  nonEmptyText,
      "sub_project_id"  ->  nonEmptyText,
      "total_land_area" ->  of[Double],
      "phase"           ->  number,
      "status"          ->  nonEmptyText,
      "address"         ->  nonEmptyText,
      "map_URL"         ->  text,
      "created_at"      ->  ignored(Instant.now)))
  def addPhotoGalleryForm = Form(tuple(
      "project_id"      ->  nonEmptyText,
      "sub_project_id"  ->  nonEmptyText,
      "is_video"        ->  ignored(false),
      "title"           ->  nonEmptyText,
      "created_at"      ->  ignored(Instant.now)))
  def addVideoGalleryForm = Form(tuple(
      "project_id"      ->  nonEmptyText,
      "sub_project_id"  ->  nonEmptyText,
      "is_video"        ->  ignored(false),
      "URL"             ->  nonEmptyText,
      "title"           ->  nonEmptyText,
      "created_at"      ->  ignored(Instant.now)))
  def addPerspectiveAndFloorPlanForm = Form(tuple(
      "project_id"      ->  nonEmptyText,
      "sub_project_id"  ->  nonEmptyText,
      "title"           ->  nonEmptyText,
      "created_at"      ->  ignored(Instant.now)))
  def addProjectForm = Form(mapping(
      "id"              ->  ignored(UUID.randomUUID),
      "name"            ->  nonEmptyText
    )(Project.apply)(Project.unapply))
  def addSubProjectForm = Form(mapping(
      "id"              ->  ignored(UUID.randomUUID),
      "name"            ->  nonEmptyText
    )(SubProject.apply)(SubProject.unapply))
  def addSalesAndMarketingForm = Form(mapping(
      "id"              ->  ignored(UUID.randomUUID),
      "title"           ->  nonEmptyText,
      "number"          ->  nonEmptyText,
      "created_at"      ->  ignored(Instant.now)
    )(SalesAndMarketing.apply)(SalesAndMarketing.unapply))
  def addSocialMediaForm = Form(mapping(
      "id"              ->  ignored(UUID.randomUUID),
      "URL"             ->  text,
      "title"           ->  nonEmptyText
    )(SocialMedia.apply)(SocialMedia.unapply))
}
