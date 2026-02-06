using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace OrgMgmt.Migrations
{
    /// <inheritdoc />
    public partial class AddReviewerTypeAndIsPublished : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_PerformanceReviews_People_ManagerId",
                table: "PerformanceReviews");

            migrationBuilder.RenameColumn(
                name: "ManagerId",
                table: "PerformanceReviews",
                newName: "ReviewerId");

            migrationBuilder.RenameIndex(
                name: "IX_PerformanceReviews_ManagerId",
                table: "PerformanceReviews",
                newName: "IX_PerformanceReviews_ReviewerId");

            migrationBuilder.AddColumn<bool>(
                name: "IsPublished",
                table: "PerformanceReviews",
                type: "bit",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<DateTime>(
                name: "PublishedDate",
                table: "PerformanceReviews",
                type: "datetime2",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "ReviewerType",
                table: "PerformanceReviews",
                type: "int",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddForeignKey(
                name: "FK_PerformanceReviews_People_ReviewerId",
                table: "PerformanceReviews",
                column: "ReviewerId",
                principalTable: "People",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_PerformanceReviews_People_ReviewerId",
                table: "PerformanceReviews");

            migrationBuilder.DropColumn(
                name: "IsPublished",
                table: "PerformanceReviews");

            migrationBuilder.DropColumn(
                name: "PublishedDate",
                table: "PerformanceReviews");

            migrationBuilder.DropColumn(
                name: "ReviewerType",
                table: "PerformanceReviews");

            migrationBuilder.RenameColumn(
                name: "ReviewerId",
                table: "PerformanceReviews",
                newName: "ManagerId");

            migrationBuilder.RenameIndex(
                name: "IX_PerformanceReviews_ReviewerId",
                table: "PerformanceReviews",
                newName: "IX_PerformanceReviews_ManagerId");

            migrationBuilder.AddForeignKey(
                name: "FK_PerformanceReviews_People_ManagerId",
                table: "PerformanceReviews",
                column: "ManagerId",
                principalTable: "People",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
