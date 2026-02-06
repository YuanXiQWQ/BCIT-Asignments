using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace OrgMgmt.Migrations
{
    /// <inheritdoc />
    public partial class AddPerformanceReviewModels : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "ReviewCycles",
                columns: table => new
                {
                    Id = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    Name = table.Column<string>(type: "nvarchar(100)", maxLength: 100, nullable: false),
                    StartDate = table.Column<DateTime>(type: "datetime2", nullable: false),
                    EndDate = table.Column<DateTime>(type: "datetime2", nullable: false),
                    Description = table.Column<string>(type: "nvarchar(500)", maxLength: 500, nullable: true),
                    IsActive = table.Column<bool>(type: "bit", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ReviewCycles", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "PerformanceReviews",
                columns: table => new
                {
                    Id = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    EmployeeId = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    ManagerId = table.Column<Guid>(type: "uniqueidentifier", nullable: true),
                    ReviewCycleId = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    Status = table.Column<int>(type: "int", nullable: false),
                    CreatedDate = table.Column<DateTime>(type: "datetime2", nullable: false),
                    SubmittedDate = table.Column<DateTime>(type: "datetime2", nullable: true),
                    FinalizedDate = table.Column<DateTime>(type: "datetime2", nullable: true),
                    OverallComments = table.Column<string>(type: "nvarchar(2000)", maxLength: 2000, nullable: true),
                    OverallRating = table.Column<int>(type: "int", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_PerformanceReviews", x => x.Id);
                    table.ForeignKey(
                        name: "FK_PerformanceReviews_People_EmployeeId",
                        column: x => x.EmployeeId,
                        principalTable: "People",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_PerformanceReviews_People_ManagerId",
                        column: x => x.ManagerId,
                        principalTable: "People",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_PerformanceReviews_ReviewCycles_ReviewCycleId",
                        column: x => x.ReviewCycleId,
                        principalTable: "ReviewCycles",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "ReviewQuestions",
                columns: table => new
                {
                    Id = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    QuestionText = table.Column<string>(type: "nvarchar(500)", maxLength: 500, nullable: false),
                    Category = table.Column<string>(type: "nvarchar(200)", maxLength: 200, nullable: true),
                    DisplayOrder = table.Column<int>(type: "int", nullable: false),
                    IsRequired = table.Column<bool>(type: "bit", nullable: false),
                    ReviewCycleId = table.Column<Guid>(type: "uniqueidentifier", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ReviewQuestions", x => x.Id);
                    table.ForeignKey(
                        name: "FK_ReviewQuestions_ReviewCycles_ReviewCycleId",
                        column: x => x.ReviewCycleId,
                        principalTable: "ReviewCycles",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "ReviewResponses",
                columns: table => new
                {
                    Id = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    PerformanceReviewId = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    ReviewQuestionId = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    ResponseText = table.Column<string>(type: "nvarchar(2000)", maxLength: 2000, nullable: true),
                    Rating = table.Column<int>(type: "int", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ReviewResponses", x => x.Id);
                    table.ForeignKey(
                        name: "FK_ReviewResponses_PerformanceReviews_PerformanceReviewId",
                        column: x => x.PerformanceReviewId,
                        principalTable: "PerformanceReviews",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_ReviewResponses_ReviewQuestions_ReviewQuestionId",
                        column: x => x.ReviewQuestionId,
                        principalTable: "ReviewQuestions",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_PerformanceReviews_EmployeeId",
                table: "PerformanceReviews",
                column: "EmployeeId");

            migrationBuilder.CreateIndex(
                name: "IX_PerformanceReviews_ManagerId",
                table: "PerformanceReviews",
                column: "ManagerId");

            migrationBuilder.CreateIndex(
                name: "IX_PerformanceReviews_ReviewCycleId",
                table: "PerformanceReviews",
                column: "ReviewCycleId");

            migrationBuilder.CreateIndex(
                name: "IX_ReviewQuestions_ReviewCycleId",
                table: "ReviewQuestions",
                column: "ReviewCycleId");

            migrationBuilder.CreateIndex(
                name: "IX_ReviewResponses_PerformanceReviewId",
                table: "ReviewResponses",
                column: "PerformanceReviewId");

            migrationBuilder.CreateIndex(
                name: "IX_ReviewResponses_ReviewQuestionId",
                table: "ReviewResponses",
                column: "ReviewQuestionId");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "ReviewResponses");

            migrationBuilder.DropTable(
                name: "PerformanceReviews");

            migrationBuilder.DropTable(
                name: "ReviewQuestions");

            migrationBuilder.DropTable(
                name: "ReviewCycles");
        }
    }
}
